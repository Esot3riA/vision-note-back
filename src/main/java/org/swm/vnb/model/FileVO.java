package org.swm.vnb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileVO {
    private Integer fileId;
    private Integer userId;
    private String originalName;
    private String savedName;
    private String extension;
    private String path;
    private Long size;
    private String createdAt;
    private String updatedAt;
}
