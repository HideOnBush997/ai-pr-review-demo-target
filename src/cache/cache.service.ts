type CacheEntry<T> = {
  value: T;
  expiresAt: number;
};

export class CacheService<T> {
  private readonly values = new Map<string, CacheEntry<T>>();

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
}
