<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@
taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %><%@
taglib prefix="form"   uri="http://www.springframework.org/tags/form" %><%@
taglib prefix="ui"     uri="http://egovframework.gov/ctl/ui"%><%@
taglib prefix="spring" uri="http://www.springframework.org/tags"%><%@
taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@
taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><!doctype html>
<html lang="ko-KR">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <title>일자리통합정보망 > 통합검색</title>
    <link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/web/images/inner/favicon.ico">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/inner/base.css?ver=1" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/inner/layout.css?ver=1" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/inner/content.css?ver=1" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/inner/respond/content.css?ver=1" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/web/css/inner/respond/layout.css?ver=1" />
    <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/web/js/inner/html5shiv.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath}/web/js/jquery-1.12.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/jquery.cookie.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/myKeyword.js"></script>
    <script src="${pageContext.request.contextPath}/web/js/inner/search.js"></script>
</head>
<body>
<div id="skipNav">
    <a href="#container">본문으로 바로가기</a>
    <a href="#gnb">주메뉴 바로가기</a>
</div>
<div id="wrap">
    <div class="mbg"></div><!--//Mobile Background-->
    <header id="header" class="start">
        <div class="util_wrap">
            <ul class="util_menu">
                <li class="on"><a href="/"><span class="logo_title"></span>일자리통합정보망</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">구인구직</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">교육훈련</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">일자리지원</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">미니잡매칭</a></li>
            </ul>
        </div>
        <div class="container">
            <h1 class="logo"><a href="/"><span class="sr-only">전라남도 교육훈련</span></a></h1>
        </div>
    </header>
    <div id="container">
        <div id="contents">
            <!--통합검색-->
            <div class="total_search">
                <div class="search_form">
                    <form id="searchForm" action="${pageContext.request.contextPath}/inner/search/search.do" method="get">
                        <fieldset>
                            <legend>통합검색</legend>
                            <div class="keyword">
                                <div class="searchbox">
                                    <input type="text" name="query" id="topQuery" class="search_box" value="<c:choose><c:when test="${query != '' && query ne null}">${query}</c:when><c:otherwise>검색어를 입력해주세요</c:otherwise></c:choose>" title="검색어를 입력해주세요">
                                    <input type="hidden" id="collection" name="collection" value="${collection}"/>
                                    <input type="hidden" id="paging" name="startCount" value=""/>
                                    <input type="hidden" id="hiddenQuery" name="hiddenQuery" value="${query}"/>
                                    <input type="hidden" id="group" name="group" value="${group}"/>
                                    <button type="submit" id="searchSubmit"><span class="blind">검색</span></button>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <p class="ment">&quot;<span class="fc_red"><strong>${query}</strong></span>&quot; 에 대한 전체 <strong>총 <span>${totalCount}</span> 건</strong>의 결과를 찾았습니다.</p>
                <div class="result">
                    <h2 class="hidden">주메뉴</h2>
                    <menu id="lmb">
                        <ul class="lm_2th">
                            <li><a href="ALL" <c:if test="${collection eq 'ALL'}">class="on"</c:if> title="MENU 통합검색 Search"><span>통합검색</span></a></li>
                            <li><a href="publicJobNet" <c:if test="${collection eq 'publicJobNet'}">class="on"</c:if> title="MENU 구인구직 Search"><span>구인구직</span></a></li>
                            <li><a href="educationTrainingNet" <c:if test="${collection eq 'educationTrainingNet'}">class="on"</c:if> title="MENU 교육훈련 Search"><span>교육훈련</span></a></li>
                            <li><a href="jobSupportNet" <c:if test="${collection eq 'jobSupportNet'}">class="on"</c:if> title="MENU 일자리지원 Search"><span>일자리지원</span></a></li>
                            <li><a href="miniJobMatchingNet" <c:if test="${collection eq 'miniJobMatchingNet'}">class="on"</c:if> title="MENU 미니잡매칭 Search"><span>미니잡매칭</span></a></li>
                        </ul>
                    </menu>
                    <div class="item">
                        <div class="total_search_box">
                            <div class="ts_wrap">
                                <c:choose>
                                    <c:when test="${collection eq 'ALL'}">
                                        <!--통합검색 박스-->
                                        <span><span class="em_b_black">통합검색</span> (총 ${totalCount}건)</span>
                                        <ul class="clearfix">
                                            <li><a class="collectionMore" href="publicJobNet">구인구직 (<span class="fc_red"><c:out value="${collectionCountMap['publicJobNetCount']}"/></span>)</a></li>
                                            <li><a class="collectionMore" href="educationTrainingNet">교육훈련 (<span class="fc_red"><c:out value="${collectionCountMap['educationTrainingNetCount']}"/></span>)</a></li>
                                            <li><a class="collectionMore" href="jobSupportNet">일자리지원 (<span class="fc_red"><c:out value="${collectionCountMap['jobSupportNetCount']}"/></span>)</a></li>
                                            <li><a class="collectionMore" href="miniJobMatchingNet">미니잡매칭 (<span class="fc_red"><c:out value="${collectionCountMap['miniJobMatchingNetCount']}"/></span>)</a></li>
                                        </ul>
                                        <!--//통합검색 박스-->
                                    </c:when>
                                    <c:otherwise>
                                        <span><span class="em_b_black"><c:choose>
                                            <c:when test="${collection eq 'publicJobNet'}">구인구직</c:when>
                                            <c:when test="${collection eq 'educationTrainingNet'}">교육훈련</c:when>
                                            <c:when test="${collection eq 'jobSupportNet'}">일자리지원</c:when>
                                            <c:when test="${collection eq 'miniJobMatchingNet'}">미니잡매칭</c:when>
                                        </c:choose></span> (총 ${totalCount}건)</span>
                                        <c:if test="${groupingsResults ne null && fn:length(groupingsResults) > 0}">
                                            <ul class="clearfix">
                                                <c:forEach var="entry" items="${groupingsResults}" varStatus="status">
                                                    <li><a class="groupMore" href="${entry['groupByCode']}">${entry['groupByName']} (<span class="fc_red">${entry['groupByCount']}</span>)</a></li>
                                                </c:forEach>
                                            </ul>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="search_sec">
                            <c:choose>
                                <c:when test="${(collection eq 'ALL' && collectionCountMap['publicJobNetCount'] > 0) || (collection eq 'publicJobNet' && group ne '' && group ne null)}">
                                    <h2>구인구직 <span>총 <c:out value="${collectionCountMap['publicJobNetCount']}"/>건</span></h2>
                                    <div class="job_box">
                                        <%--<dl>
                                            <dt>
                                                <a href="/">이랜드하당노인복지관</a>
                                            </dt>
                                            <dd>
                                                <p><!--채용제목-->하당노인복지관 노인<!--HS-->일자리<!--HE--> 및 사회활동지원사업 전담...</p>
                                                <span><strong>마감일 : </strong>2017-12-15(금)</span>
                                                <span><strong>경력 : </strong>경력무관</span>
                                                <span><strong>학력 : </strong>고졸 ~ 대졸(4년)</span>
                                                <span><strong>고용형태 : </strong>월급</span>
                                                <span class="bg_none"><strong>근무지역 : </strong>전남 목포시</span>
                                                <span class="db bg_none"><strong>키워드 : </strong>전담인력,노인<!--HS-->일자리</span>
                                                <em>홈 &gt; 구인구직 &gt; 채용정보</em>
                                            </dd>
                                        </dl>
                                        <ul class="img_list">
                                            <li>
                                                <a href="/" title="" target="_blank">
                                                    <span class="title">[공지사항]<strong>2014년 전라남도 일자리 취업 박람회 개최</strong></span>
                                                    <span class="date">2018.10.11</span>
                                                    <p>행사명 : 2017년 전남동부권 <!--HS-->일자리<!--HE--> 박람회 일 시 : 2017. 11. 16(목) 10:00~17:00 장 소 : 순천팔마체육관 주 최 : 여수고용노동지청, 전라남도, 광양만권경제자유구역청,한국산업단지공단여수광양지사,전남테크노파크 주 관 : 광양만권<!--HS-->일자리<!--HE-->사업단 ※ 자세한 사항은 첨부파일 및 http://2017jnjobfair.com/ 으로 문의 바랍니다.
                                                    </p>
                                                </a>
                                                <em>홈 &gt; 구인구직 &gt; 게시판</em>
                                            </li>
                                        </ul>--%>
                                        <c:forEach var="entry" items="${collectionResultMap['publicJobNetResult']}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${entry['ALIAS'] eq 'careers'}">
                                                    <dl>
                                                        <dt>[채용정보]<a href="${serviceDomain}/spm/employ/employView.do?menuCd=${entry['MENU_CD']}&seekPersonNo=${entry['LINK_ID']}&bUserNo=${entry['BUSER_NO']}&wantedAuthNo=${entry['WANTED_AUTH_NO']}&listTarget=${entry['LIST_TARGET']}">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                            <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                            <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                            <span><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                            <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:when test="${entry['ALIAS']  eq 'publicJobs'}">
                                                    <dl>
                                                        <dt>[공공일자리] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>등록일 : </strong>${entry['RDATE']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:when test="${entry['ALIAS']  eq 'publicAgencyJobs'}">
                                                    <dl>
                                                        <dt>[공공기관채용정보] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>등록일 : </strong>${entry['RDATE']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:when test="${entry['ALIAS']  eq 'publicRecruitment'}">
                                                    <dl>
                                                        <dt>[공채속보] <a href="${serviceDomain}/spm/openEmploy/openEmployView.do?openEmployNo=${entry['LINK_ID']}&menuCd=${entry['MENU_CD']}">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>회사명 : </strong>${entry['COMPANY_NAME']}</span>
                                                            <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                            <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                            <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                            <span class="bg_none"><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                            <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:when test="${entry['ALIAS']  eq 'jobPolicy'}">
                                                    <dl>
                                                        <dt>[일자리정책,사업/${entry['BOARD_NAME']}] <a href="${serviceDomain}<c:choose><c:when
                                                            test="${fn:contains(entry['DOCID'], 'JOBPOLICYONE')}">/spt/bus/busView.do?menuCd=${entry['MENU_CD']}&hopeBusNo=${entry['LINK_ID']}</c:when><c:when
                                                            test="${fn:contains(entry['DOCID'], 'JOBPOLICYTWO')}">/spt/match/matchView.do?menuCd=${entry['MENU_CD']}&matchNo=${entry['LINK_ID']}</c:when><c:when
                                                            test="${fn:contains(entry['DOCID'], 'JOBPOLICYTHREE')}">/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}</c:when><c:when
                                                            test="${fn:contains(entry['DOCID'], 'JOBPOLICYFORU')}">/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}</c:when></c:choose>">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>회사명 : </strong>${entry['COMPANY_NAME']}</span>
                                                            <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                            <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                            <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                            <span class="bg_none"><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                            <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:when test="${entry['ALIAS']  eq 'goodCompany'}">
                                                    <dl>
                                                        <dt>[전남우수기업]<a href="${serviceDomain}/companyInfo/bestCompany/bestCompanyView.do?menuCd=${entry['MENU_CD']}&bestCompanyNo=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                            <span><strong>주요제품 : </strong>${entry['MAIN_PRODUCT']}</span>
                                                            <span><strong>전화번호 : </strong>${entry['PHONE_NUMBER']}</span>
                                                        </dd>
                                                    </dl>
                                                </c:when>
                                                <c:otherwise><%--publicJobBbs--%>
                                                    <dl>
                                                        <dt>
                                                            [게시판/${entry['BOARD_NAME']}] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a>
                                                        </dt>
                                                        <dd>
                                                            <p>${entry['CONTENT']}</p>
                                                        </dd>
                                                    </dl>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${collection eq 'ALL' && collectionCountMap['publicJobNetCount'] > 3}">
                                            <span class="TxtR"><a class="collectionMore" href="publicJobNet">구인구직 검색결과 더보기 +</a></span>
                                        </c:if>
                                    </div>
                                </c:when>
                                <c:when test="${collection eq 'publicJobNet' && (group eq '' || group eq null)}">
                                    <c:forEach var="groupResult" items="${collectionGroupResultMap}" varStatus="status">
                                        <h2><c:out value="${groupResult.aliasText}"/> <span>총 <c:out value="${groupResult.count}"/>건</span></h2>
                                        <div class="job_box">
                                            <c:forEach var="entry" items="${groupResult.documents}" varStatus="status">
                                                <c:choose>
                                                    <c:when test="${entry['ALIAS'] eq 'careers'}">
                                                        <dl>
                                                            <dt>[채용정보]<a href="${serviceDomain}/spm/employ/employView.do?menuCd=${entry['MENU_CD']}&seekPersonNo=${entry['LINK_ID']}&bUserNo=${entry['BUSER_NO']}&wantedAuthNo=${entry['WANTED_AUTH_NO']}&listTarget=${entry['LIST_TARGET']}">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                                <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                                <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                                <span><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                                <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:when test="${entry['ALIAS']  eq 'publicJobs'}">
                                                        <dl>
                                                            <dt>[공공일자리] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>등록일 : </strong>${entry['RDATE']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:when test="${entry['ALIAS']  eq 'publicAgencyJobs'}">
                                                        <dl>
                                                            <dt>[공공기관채용정보] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>등록일 : </strong>${entry['RDATE']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:when test="${entry['ALIAS']  eq 'publicRecruitment'}">
                                                        <dl>
                                                            <dt>[공채속보] <a href="${serviceDomain}/spm/openEmploy/openEmployView.do?openEmployNo=${entry['LINK_ID']}&menuCd=${entry['MENU_CD']}">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>회사명 : </strong>${entry['COMPANY_NAME']}</span>
                                                                <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                                <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                                <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                                <span class="bg_none"><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                                <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:when test="${entry['ALIAS']  eq 'jobPolicy'}">
                                                        <dl>
                                                            <dt>[일자리정책,사업/${entry['BOARD_NAME']}] <a href="${serviceDomain}<c:choose><c:when
                                                                    test="${fn:contains(entry['DOCID'], 'JOBPOLICYONE')}">/spt/bus/busView.do?menuCd=${entry['MENU_CD']}&hopeBusNo=${entry['LINK_ID']}</c:when><c:when
                                                                    test="${fn:contains(entry['DOCID'], 'JOBPOLICYTWO')}">/spt/match/matchView.do?menuCd=${entry['MENU_CD']}&matchNo=${entry['LINK_ID']}</c:when><c:when
                                                                    test="${fn:contains(entry['DOCID'], 'JOBPOLICYTHREE')}">/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}</c:when><c:when
                                                                    test="${fn:contains(entry['DOCID'], 'JOBPOLICYFORU')}">/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}</c:when></c:choose>">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>회사명 : </strong>${entry['COMPANY_NAME']}</span>
                                                                <span><strong>마감일 : </strong>${entry['RDATE']}</span>
                                                                <span><strong>경력 : </strong>${entry['CAREER']}</span>
                                                                <span><strong>학력 : </strong>${entry['EDUCATION']}</span>
                                                                <span class="bg_none"><strong>고용형태 : </strong>${entry['EMPLOYMENT_TYPE']}</span>
                                                                <span class="bg_none"><strong>근무지역 : </strong>${entry['WORKING_AREA']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:when test="${entry['ALIAS']  eq 'goodCompany'}">
                                                        <dl>
                                                            <dt>[전남우수기업]<a href="${serviceDomain}/companyInfo/bestCompany/bestCompanyView.do?menuCd=${entry['MENU_CD']}&bestCompanyNo=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                                <span><strong>주요제품 : </strong>${entry['MAIN_PRODUCT']}</span>
                                                                <span><strong>전화번호 : </strong>${entry['PHONE_NUMBER']}</span>
                                                            </dd>
                                                        </dl>
                                                    </c:when>
                                                    <c:otherwise><%--publicJobBbs--%>
                                                        <dl>
                                                            <dt>
                                                                [게시판/${entry['BOARD_NAME']}] <a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a>
                                                            </dt>
                                                            <dd>
                                                                <p>${entry['CONTENT']}</p>
                                                            </dd>
                                                        </dl>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                            <c:if test="${(collection eq 'ALL' && collectionCountMap['educationTrainingNetCount'] > 0) || collection eq 'educationTrainingNet'}">
                                <h2>교육훈련 <span>총 <c:out value="${collectionCountMap['educationTrainingNetCount']}"/>건</span></h2>
                                <div class="job_box">
                                    <c:forEach var="entry" items="${collectionResultMap['educationTrainingNetResult']}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${entry['ALIAS'] eq 'eduOrgan'}"><%--기관소개--%>
                                                <dl>
                                                    <dt>[기관소개]<a href="${serviceDomain}/edu/orgIntro/orgIntroView.do?menuNo=&orgIntroId=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                        <span><strong>지역 : </strong>${entry['LOCATION']}</span>
                                                        <span><strong>전화번호 : </strong>${entry['TEL_NO']}</span>
                                                        <span><strong>홈페이지 : </strong>${entry['HOMEPAGE']}</span>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:when test="${entry['ALIAS']  eq 'eduInfo'}"><%--교육훈련명--%>
                                                <dl>
                                                    <dt>[교육훈련명] <a href="${serviceDomain}/edu/education/educationView.do?menuCd=${entry['MENU_CD']}&listTarget=education&eduInfoNo=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                        <span><strong>교육기관 : </strong>${entry['COMPANY_NAME']}</span>
                                                        <span><strong>모집기간 : </strong>${entry['RECRUIT_START_DT']} ~ ${entry['RECRUIT_END_DT']}</span>
                                                        <span><strong>교육정원 : </strong>${entry['EDU_MAX_COUNT']} 명</span>
                                                        <span class="bg_none"><strong>교육시간 : </strong>${entry['EDU_TIME']}</span>
                                                        <span class="bg_none"><strong>교육장소 : </strong>${entry['EDU_ADDR1']}</span>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:when test="${entry['ALIAS'] eq 'eduBbs'}"><%--게시판--%>
                                                <dl>
                                                    <dt>[게시판/${entry['BOARD_NAME']}]<a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${collection eq 'ALL' && collectionCountMap['educationTrainingNetCount'] > 3}">
                                        <span class="TxtR"><a class="collectionMore" href="educationTrainingNet">교육훈련 검색결과 더보기 +</a></span>
                                    </c:if>
                                </div>
                            </c:if>
                            <c:if test="${(collection eq 'ALL' && collectionCountMap['jobSupportNetCount'] > 0) || collection eq 'jobSupportNet'}">
                                <h2>일자리지원 <span>총 <c:out value="${collectionCountMap['jobSupportNetCount']}"/>건</span></h2>
                                <div class="job_box">
                                    <c:forEach var="entry" items="${collectionResultMap['jobSupportNetResult']}" varStatus="status">
                                        <c:choose>
                                            <c:when test="${entry['ALIAS'] eq 'bussinessJang'}"><%--사업장--%>
                                                <dl>
                                                    <dt>[사업장] <a href="${serviceDomain}/work/work/workView.do?menuCd=${entry['MENU_CD']}&workId=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                        <span><strong>회차명 : </strong>${entry['OFFER_NAME']}</span>
                                                        <span><strong>회사명 : </strong>${entry['COMPANY_NAME']}</span>
                                                        <span><strong>업종 : </strong> ${entry['WORK_KIND']}</span>
                                                        <span class="bg_none"><strong>소재지 : </strong>${entry['COMPANY_ADDR1']}</span>
                                                        <span class="bg_none"><strong>근무형태 : </strong>${entry['WORK_TYPE']}</span>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:when test="${entry['ALIAS'] eq 'bussinessBbs'}"><%--게시판--%>
                                                <dl>
                                                    <dt>[게시판/${entry['BOARD_NAME']}]<a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${collection eq 'ALL' && collectionCountMap['jobSupportNetCount'] > 3}">
                                        <span class="TxtR"><a class="collectionMore" href="jobSupportNet">일자리지원 검색결과 더보기 +</a></span>
                                    </c:if>
                                </div>
                            </c:if>
                            <c:if test="${(collection eq 'ALL' && collectionCountMap['miniJobMatchingNetCount'] > 0) || collection eq 'miniJobMatchingNet'}">
                                <h2>미니잡매칭 <span>총 <c:out value="${collectionCountMap['miniJobMatchingNetCount']}"/>건</span></h2>
                                <div class="job_box">
                                    <c:forEach var="entry" items="${collectionResultMap['miniJobMatchingNetResult']}" varStatus="status">
                                <%--DOCID,LINK_ID,TITLE,CONTENT,COMPANY_NM,COMPANY_JOB,TEL_NO,INSERT_DT,INFOSUB_ID,KIND_NM,HIRE_CNT,PAY,EDUCATION,CAREER,MENU_CD,ALIAS--%>
                                        <c:choose>
                                            <c:when test="${entry['ALIAS'] eq 'miniJob'}"><%--사업장--%>
                                                <dl>
                                                    <dt>[미니잡매칭] <a href="${serviceDomain}/fair/job/sjinfo/infoView.do?menuCd=${entry['MENU_CD']}&infoId=${entry['LINK_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                        <span><strong>회사명 : </strong>${entry['COMPANY_NM']}</span>
                                                        <span><strong>업종 : </strong> ${entry['COMPANY_JOB']}</span>
                                                        <span><strong>모집직종 : </strong> ${entry['KIND_NM']}</span>
                                                        <span class="bg_none"><strong>급여 : </strong> ${entry['PAY']}</span>
                                                        <span class="bg_none"><strong>학력 : </strong> ${entry['EDUCATION']}</span>
                                                        <span class="bg_none"><strong>경력 : </strong> ${entry['CAREER']}</span>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:when test="${entry['ALIAS'] eq 'miniJobBbs'}"><%--게시판--%>
                                                <dl>
                                                    <dt>[게시판/${entry['BOARD_NAME']}]<a href="${serviceDomain}/board/${entry['BOARD_ID']}/boardView.do?menuCd=${entry['MENU_CD']}&boardId=${entry['BOARD_ID']}&bdId=${entry['BD_ID']}">${entry['TITLE']}</a></dt>
                                                    <dd>
                                                        <p>${entry['CONTENT']}</p>
                                                    </dd>
                                                </dl>
                                            </c:when>
                                            <c:otherwise></c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${collection eq 'ALL' && collectionCountMap['miniJobMatchingNetCount'] > 3}">
                                        <span class="TxtR"><a class="collectionMore" href="miniJobMatchingNet">미니잡매칭 검색결과 더보기 +</a></span>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>
                        <c:if test="${totalCount > 10 && collection ne 'ALL' && group ne ''}">
                            <!--page navigation-->
                            <div class="paging_wrap">
                                <div class="tac">
                                    <ul class="pagination">
                                        <li class="button previous"><a href="#" title="처음" <c:if test="${startCount ne 0}">onClick="javascript:doPaging('0');"</c:if>><span>처음</span></a></li>
                                            ${paging}
                                        <li class="button next"><a href="#" title="마지막" <c:if test="${totalCount ne 0}">onClick="javascript:doPaging('${lastPaging}');"</c:if>><span>마지막</span></a></li>
                                    </ul>
                                </div>
                            </div>
                            <!--//page navigation-->
                        </c:if>
                    </div>
                    <article class="rangking">
                        <!--랭킹메뉴-->
                        <article class="group" id="myKeywordAreaDiv">
                            <h3>내가 찾은 검색어</h3>
                            <ul class="list" id="myKeywordArea">
                                <li>
                                    <small>1</small>
                                    <a href="javascript:;" onclick="">
                                        <span>일자리</span>
                                    </a>
                                </li>
                            </ul>
                        </article>
                        <c:if test="${frequentlyAskedMenus ne null && fn:length(frequentlyAskedMenus) > 0}">
                        <article class="group">
                            <h3>많이 찾는 메뉴</h3>
                            <ul class="list">
                                <c:forEach var="item" items="${frequentlyAskedMenus}" varStatus="status" end="4">
                                    <li>
                                        <small>${status.index + 1}</small>
                                        <a href="${item.menuUrl}" target="_blank"><span>${item.menuNm}</span></a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </article>
                        </c:if>
                        <c:if test="${(weeklyPopKeywords ne null && fn:length(weeklyPopKeywords) > 0) || (dailyPopKeywords ne null && fn:length(dailyPopKeywords))}">
                            <article class="group" id="popkeywordtabarea">
                                <h3>인기검색어</h3>
                                <ul class="menu">
                                    <li class="on"><a class="popkeywordtab" href="daily">일간</a></li>
                                    <li><a class="popkeywordtab" href="weekly">주간</a></li>
                                </ul>
                                <ul class="list" id="dailyPopkeywords" style="display: block">
                                    <c:forEach var="popKeyword" items="${dailyPopKeywords}" varStatus="status" end="4">
                                        <li><small>${status.index + 1}</small><a href="${popKeyword}" class="otherKeyword"><span>${popKeyword}</span></a></li>
                                    </c:forEach>
                                </ul>
                                <ul class="list" id="weeklyPopkeywords" style="display: none">
                                    <c:forEach var="popKeyword" items="${weeklyPopKeywords}" varStatus="status" end="4">
                                        <li><small>${status.index + 1}</small><a href="${popKeyword}" class="otherKeyword"><span>${popKeyword}</span></a></li>
                                    </c:forEach>
                                </ul>
                            </article>
                        </c:if>
                        <c:if test="${realTimeKeywords ne null && fn:length(realTimeKeywords) > 0}">
                            <article class="group">
                                <h3>실시간 검색어</h3>
                                <ul class="list">
                                    <c:forEach var="realTimKeyword" items="${realTimeKeywords}" varStatus="status" end="4">
                                        <li><small>${status.index + 1}</small><a href="${realTimKeyword}" class="otherKeyword"><span>${realTimKeyword}</span></a></li>
                                    </c:forEach>
                                </ul>
                            </article>
                        </c:if>
                        <!--//랭킹메뉴-->
                    </article>
                </div>
            </div>
            <!--//통합검색-->
        </div>
    </div>
    <footer id="footer">
        <div class="footWrap">
            <ul class="linkArea">
                <li><a href="/" target="_blank" title="새창으로 열림">개인정보보호정책</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">이메일무단수집거부</a></li>
                <li><a href="/" target="_blank" title="새창으로 열림">회원약관</a></li>
            </ul>
            <aside class="relate_site">
                <div>
                    <a href="#" class="button">관련사이트 바로가기<span></span></a>
                    <ul style="display: none;">
                        <li><a href="http://www.jeonnam.go.kr" target="_blank" title="새창으로 열림">전라남도청</a></li>
                        <li><a href="http://www.jepa.kr/" target="_blank" title="새창으로 열림">전라남도 중소기업진흥원</a></li>
                    </ul>
                </div>
            </aside>
            <div class="footer_box">
                <span class="logo"><img src="${pageContext.request.contextPath}/web/images/inner/common/f_logo.png" alt="일자리통합정보망"></span>
                <div class="footer_link">
                    <address>(우)58565 전라남도 무안군 삼향읍 오룡1길 37 (1층)    /   대표전화 061-287-1142~3     /   팩스번호 061-287-1146</address>
                    <p class="copyright">COPYRIGHT(C) 2018 Jeollanam-do. All rights reserved</p>
                </div>
            </div>
        </div>
    </footer>
    <a href="#wrap" class="topBt over" title="본문상단으로 이동">TOP</a>
</div>
</body>
</html>