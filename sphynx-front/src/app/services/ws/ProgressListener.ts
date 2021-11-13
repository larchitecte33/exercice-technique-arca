import {ProgressDto} from "../../model/ProgressDto";

export interface ProgressListener {
  onProgress (progress: ProgressDto)
}
