import {UserInfoResponse} from "../../auth/types/response/user-info-response";

export interface CommentDto {
  commentID?: number;
  issueID?: number;
  body: string;
  author?: UserInfoResponse;
}
