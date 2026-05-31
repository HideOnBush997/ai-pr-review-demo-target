type CacheEntry<T> = {
  value: T;
  expiresAt: number;
};

export class CacheService<T> {
  private readonly values = new Map<string, CacheEntry<T>>();
  private readonly refreshes = new Map<string, Promise<T>>();

  set(key: string, value: T, ttlMs: number) {
    this.values.set(key, {
      value,
      expiresAt: Date.now() + ttlMs,
    });
  }

  get(key: string): T | undefined {
    const entry = this.values.get(key);
    if (!entry) {
      return undefined;
    }
    if (entry.expiresAt <= Date.now()) {
      this.values.delete(key);
      return undefined;
    }
    return entry.value;
  }

  async getOrRefresh(
    key: string,
    ttlMs: number,
    refresh: () => Promise<T>,
  ): Promise<T> {
    const cached = this.get(key);
    if (cached) {
      return cached;
    }

    const pending = refresh();
    this.refreshes.set(key, pending);
    const value = await refresh();
    this.set(key, value, ttlMs);
    this.refreshes.delete(key);
    return value;
  }
}
