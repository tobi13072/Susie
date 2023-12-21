import { Injectable } from '@angular/core';
import {env} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {CommentDto} from "../types/comment-dto";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentsService {
  private readonly COMMENT_PATH: string = env.apiUrl.concat("/comment");
  constructor(private http: HttpClient) { }

  sendComment(comment: CommentDto): Observable<CommentDto>{
    return this.http.post<CommentDto>(this.COMMENT_PATH, comment)
  }

  deleteComment(commentId: number): Observable<any>{
    return this.http.delete<any>(this.COMMENT_PATH.concat(`/${commentId}`))
  }
}
