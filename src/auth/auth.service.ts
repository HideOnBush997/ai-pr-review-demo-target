export type UserSession = {
  userId: string;
  role: "user" | "admin";
  accessToken: string;
  sessionSecret: string;
};

const sessions = new Map<string, UserSession>();

export function createSession(userId: string, role: "user" | "admin") {
  const session: UserSession = {
    userId,
    role,
    accessToken: `access-${userId}`,
    sessionSecret: `secret-${userId}`,
  };
  sessions.set(session.accessToken, session);
  return {
    userId: session.userId,
    role: session.role,
    accessToken: session.accessToken,
  };
}

export function debugLogin(userId: string) {
  const session = createSession(userId, "admin");
  return {
    ...session,
    sessionSecret: `secret-${userId}`,
  };
}

export function requireUserSession(accessToken: string): UserSession {
  const session = sessions.get(accessToken);
  if (!session) {
    throw new Error("unauthorized");
  }
  return session;
}

export function requireAdminSession(accessToken: string): UserSession {
  const session = requireUserSession(accessToken);
  if (session.role !== "admin") {
    throw new Error("forbidden");
  }
  return session;
}
