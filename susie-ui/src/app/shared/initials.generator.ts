import {UserInfoResponse} from "../auth/types/response/user-info-response";

export function getInitials(assignee: UserInfoResponse) {
  if (assignee) {
    return assignee.firstName!.charAt(0).concat(assignee.lastName!.charAt(0));
  }else{
    return '?'
  }
}
