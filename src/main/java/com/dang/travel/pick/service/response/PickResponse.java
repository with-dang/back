package com.dang.travel.pick.service.response;

public record PickResponse(
	// TourItem 관련 필드
	String contentid,
	String contenttypeid,
	String title,
	String createdtime,
	String modifiedtime,
	String tel,
	String telname,
	String homepage,
	String booktour,
	String firstimage,
	String firstimage2,
	String cpyrhtDivCd,
	String areacode,
	String sigungucode,
	String cat1,
	String cat2,
	String cat3,
	String addr1,
	String addr2,
	String zipcode,
	String mapx,
	String mapy,
	String mlevel,
	String overview,

	// PetItem 관련 필드
	String petTursmInfo,
	String relaAcdntRiskMtr,
	String acmpyTypeCd,
	String relaPosesFclty,
	String relaFrnshPrdlst,
	String etcAcmpyInfo,
	String relaPurcPrdlst,
	String acmpyPsblCpam,
	String relaRntlPrdlst,
	String acmpyNeedMtr
) {
	// 생성자를 통해 TourItem과 PetItem으로부터 값을 채움
	public PickResponse(TourItem tourItem, PetItem petItem) {
		this(
			tourItem.contentid(),
			tourItem.contenttypeid(),
			tourItem.title(),
			tourItem.createdtime(),
			tourItem.modifiedtime(),
			tourItem.tel(),
			tourItem.telname(),
			tourItem.homepage(),
			tourItem.booktour(),
			tourItem.firstimage(),
			tourItem.firstimage2(),
			tourItem.cpyrhtDivCd(),
			tourItem.areacode(),
			tourItem.sigungucode(),
			tourItem.cat1(),
			tourItem.cat2(),
			tourItem.cat3(),
			tourItem.addr1(),
			tourItem.addr2(),
			tourItem.zipcode(),
			tourItem.mapx(),
			tourItem.mapy(),
			tourItem.mlevel(),
			tourItem.overview(),

			// PetItem 관련 필드
			petItem.petTursmInfo(),
			petItem.relaAcdntRiskMtr(),
			petItem.acmpyTypeCd(),
			petItem.relaPosesFclty(),
			petItem.relaFrnshPrdlst(),
			petItem.etcAcmpyInfo(),
			petItem.relaPurcPrdlst(),
			petItem.acmpyPsblCpam(),
			petItem.relaRntlPrdlst(),
			petItem.acmpyNeedMtr()
		);
	}
}

