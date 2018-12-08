package egovframework.search.inner.common;


public enum GROUP_BY_CDOE {

    publicJobBbs("게시판", "publicJobNet"),
    publicAgencyJobs("공공기관채용정보", "publicJobNet"),
    careers("채용정보", "publicJobNet"),
    publicRecruitment("공채속보", "publicJobNet"),
    goodCompany("전남우수기업", "publicJobNet"),
    jobPolicyBus("청년희망버스", "publicJobNet"),
    jobPolicyMeet("구인구직만남의날", "publicJobNet"),
    jobPolicyCenter("일자리상담센터", "publicJobNet"), // 찾아가는일자리상담센터
    jobPolicyDay("잡매칭데이", "publicJobNet"),
    eduOrgan("기관소개", "publicJobNet"),
    eduInfo("교육훈련정보", "educationTrainingNet"),
    eduBbs("게시판", "educationTrainingNet"),
    bussinessJang("사업장", "jobSupportNet"),
    bussinessBbs("게시판", "jobSupportNet"),
    miniJob("채용정보", "miniJobMatchingNet"),
    miniJobBbs("게시판", "miniJobMatchingNet");

    private String text;
    private String collection;

    GROUP_BY_CDOE(String text, String collection) {
        this.text = text;
        this.collection = collection;
    }
    public String getText() {
        return this.text;
    }
    public String getCollection() {
        return this.collection;
    }
}