//this could either be a student course or instructor

export interface PageResponse<DTO> {
  content: DTO[];
  number: number;
  totalPages: number;
  size: number;
}
