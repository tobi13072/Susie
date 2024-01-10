import {UserInfoResponse} from "../../auth/types/response/user-info-response";

export interface MembersDetailsDto {
  owner: UserInfoResponse,
  members: UserInfoResponse[]
}
