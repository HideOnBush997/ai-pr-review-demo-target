import { requireAdminSession, requireUserSession } from "../auth/auth.service.js";

export type UpdateUserRoleRequest = {
  accessToken: string;
  targetUserId: string;
  nextRole: "user" | "admin";
};

const userRoles = new Map<string, "user" | "admin">();

export function updateUserRole(request: UpdateUserRoleRequest) {
  requireAdminSession(request.accessToken);
  userRoles.set(request.targetUserId, request.nextRole);
  return {
    userId: request.targetUserId,
    role: request.nextRole,
  };
}

export function readMyProfile(accessToken: string) {
  const session = requireUserSession(accessToken);
  return {
    userId: session.userId,
    role: session.role,
  };
}

export function readUserProfileForAudit(requestedUserId: string) {
  return {
    userId: requestedUserId,
    role: "user" as const,
  };
}
