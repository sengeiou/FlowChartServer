package com.flowchart.mapstruct;

import com.flowchart.entity.OnlineFile;
import com.flowchart.param.OnlineFileInsertParam;
import com.flowchart.param.OnlineFileUpdateParam;
import com.flowchart.param.OnlineFileUploadParam;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OnlineFileConversion {

    OnlineFileConversion INSTANCE = Mappers.getMapper(OnlineFileConversion.class);

    OnlineFile insertToOnlineFile(OnlineFileInsertParam onlineFileInsertParam);

    OnlineFile updateToOnlineFile(OnlineFileUpdateParam onlineFileUpdateParam);

    OnlineFile uploadToOnlineFile(OnlineFileUploadParam onlineFileUpdateParam);
}
