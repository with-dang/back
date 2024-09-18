package com.dang.travel.pick.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PetItem(
	@JsonProperty("contentid") String contentId,
	@JsonProperty("petTursmInfo") String petTursmInfo,
	@JsonProperty("relaAcdntRiskMtr") String relaAcdntRiskMtr,
	@JsonProperty("acmpyTypeCd") String acmpyTypeCd,
	@JsonProperty("relaPosesFclty") String relaPosesFclty,
	@JsonProperty("relaFrnshPrdlst") String relaFrnshPrdlst,
	@JsonProperty("etcAcmpyInfo") String etcAcmpyInfo,
	@JsonProperty("relaPurcPrdlst") String relaPurcPrdlst,
	@JsonProperty("acmpyPsblCpam") String acmpyPsblCpam,
	@JsonProperty("relaRntlPrdlst") String relaRntlPrdlst,
	@JsonProperty("acmpyNeedMtr") String acmpyNeedMtr
) {
}
