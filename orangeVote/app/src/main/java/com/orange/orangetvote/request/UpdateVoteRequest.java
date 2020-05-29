package com.orange.orangetvote.request;

import com.orange.orangetvote.basic.request.AbstractRequest;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVoteRequest extends AbstractRequest {

    public UpdateVoteRequest(String voteUuid, String voteName, String content, String teamUuid, String expiredDate,
        List<String> updateOptionUuids, List<String> updateOptionValues, List<String> deleteOptionUuids,
        List<String> addOptionValues, boolean isAllowAdd, boolean isOpen, boolean isSign, int multiSelection) {

        this.voteUuid = voteUuid;
        this.voteName = voteName;
        this.content = content;
        this.teamUuid = teamUuid;
        this.expiredDate = expiredDate;
        this.updateOptionUuids = updateOptionUuids;
        this.updateOptionValues = updateOptionValues;
        this.deleteOptionUuids = deleteOptionUuids;
        this.addOptionValues = addOptionValues;
        this.isAllowAdd = isAllowAdd;
        this.isOpen = isOpen;
        this.isSign = isSign;
        this.multiSelection = multiSelection;

    }

    private String voteUuid;

    private String voteName;

    private String content;

    private String teamUuid;

    private String expiredDate;

    private List<String> updateOptionUuids;

    private List<String> updateOptionValues;

    private List<String> deleteOptionUuids;

    private List<String> addOptionValues;

    private Boolean isAllowAdd;

    private Boolean isOpen;

    private Boolean isSign;

    private int multiSelection;
}
