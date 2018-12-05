package egovframework.search.inner.common;

public class WNCollection {
	
	public static String SEARCH_IP = "dev.truetech.info"; //"211.39.94.35";
	public static int SEARCH_PORT = 7000;
	public static String MANAGER_IP = "211.39.94.35";
	public static int MANAGER_PORT = 7800;

	public static String[] COLLECTIONS = new String[]{"publicJobNet", "educationTrainingNet", "jobSupportNet", "miniJobMatchingNet"};
	public static String[] COLLECTIONS_NAME = new String[]{"publicJobNet", "educationTrainingNet", "jobSupportNet", "miniJobMatchingNet"};
	public static String[] MERGE_COLLECTIONS = new String[]{""};
	public String[][] MERGE_COLLECTION_INFO = null;
	public String[][] COLLECTION_INFO = null;
	public WNCollection(){
		COLLECTION_INFO = new String[][] {
			{
				"publicJobNet", // set index name
				"publicJobNet", // set collection name
				"0,3",  // set pageinfo (start,count)
				"1,0,0,0,0", // set query analyzer (useKMA,isCase,useOriginal,useSynonym, duplcated detection)
				"RANK/DESC,DATE/DESC",  // set sort field (field,order) multi sort '/'
				"basic,rpfmo,100",  // set sort field (field,order) multi sort '/'
				"TITLE/100,CONTENT/50",// set search field
				"DOCID,LINK_ID,TITLE,CONTENT,RDATE,CAREER,EDUCATION,EMPLOYMENT_TYPE,WORKING_AREA,COMPANY_NAME,BUSER_NO,WANTED_AUTH_NO,LIST_TARGET,MAIN_PRODUCT,PHONE_NUMBER,BOARD_ID,BOARD_NAME,BD_ID,MENU_CD,ALIAS",// set document field
				"", // set date range
				"", // set rank range
				"", // set prefix query, example: <fieldname:contains:value1>|<fieldname:contains:value2>/1,  (fieldname:contains:value) and ' ', or '|', not '!' / operator (AND:1, OR:0)
				"", // set collection query (<fieldname:contains:value^weight | value^weight>/option...) and ' ', or '|'
				"", // set boost query (<fieldname:contains:value> | <field3:contains:value>...) and ' ', or '|'
				"", // set filter operation (<fieldname:operator:value>)
				"", // set groupby field(field, count)
				"", // set sort field group(field/order,field/order,...)
				"", // set categoryBoost(fieldname,matchType,boostID,boostKeyword)
				"", // set categoryGroupBy (fieldname:value)
				"", // set categoryQuery (fieldname:value)
				"", // set property group (fieldname,min,max, groupcount)
				"", // use check prefix query filed
				"ALIAS", // set use check fast access field
				"", // set multigroupby field
				"", // set auth query (Auth Target Field, Auth Collection, Auth Reference Field, Authority Query)
				"", // set Duplicate Detection Criterion Field, RANK/DESC,DATE/DESC
				"구인구직" // collection display name
			},
			{
				"educationTrainingNet", // set index name
				"educationTrainingNet", // set collection name
				"0,3",  // set pageinfo (start,count)
				"1,0,0,0,0", // set query analyzer (useKMA,isCase,useOriginal,useSynonym, duplcated detection)
				"RANK/DESC,DATE/DESC",  // set sort field (field,order) multi sort '/'
				"basic,rpfmo,100",  // set sort field (field,order) multi sort '/'
				"TITLE/100,CONTENT/50",// set search field
				"DOCID,LINK_ID,BD_ID,BOARD_NAME,TITLE,CONTENT,COMPANY_NAME,TEL_NO,HOMEPAGE,EMAIL,EDU_MAX_COUNT,EDU_ADDR1,EDU_ADDR2,EDU_PURPOSE,LOCATION,EDU_TIME,RECRUIT_START_DT,RECRUIT_END_DT,EDU_START_DT,EDU_END_DT,INSERT_DT,INSERT_ID,UPDATE_DT,UPDATE_ID,ALIAS", // set document field :TODO , --> MENU_CD 해당 필드를 넣으면 검색이 안됨
				"", // set date range
				"", // set rank range
				"", // set prefix query, example: <fieldname:contains:value1>|<fieldname:contains:value2>/1,  (fieldname:contains:value) and ' ', or '|', not '!' / operator (AND:1, OR:0)
				"", // set collection query (<fieldname:contains:value^weight | value^weight>/option...) and ' ', or '|'
				"", // set boost query (<fieldname:contains:value> | <field3:contains:value>...) and ' ', or '|'
				"", // set filter operation (<fieldname:operator:value>)
				"", // set groupby field(field, count)
				"", // set sort field group(field/order,field/order,...)
				"", // set categoryBoost(fieldname,matchType,boostID,boostKeyword)
				"", // set categoryGroupBy (fieldname:value)
				"", // set categoryQuery (fieldname:value)
				"", // set property group (fieldname,min,max, groupcount)
				"", // use check prefix query filed
				"ALIAS", // set use check fast access field
				"", // set multigroupby field
				"", // set auth query (Auth Target Field, Auth Collection, Auth Reference Field, Authority Query)
				"", // set Duplicate Detection Criterion Field, RANK/DESC,DATE/DESC
				"교육훈련" // collection display name
			},
			{
				"jobSupportNet", // set index name
				"jobSupportNet", // set collection name
				"0,3",  // set pageinfo (start,count)
				"1,0,0,0,0", // set query analyzer (useKMA,isCase,useOriginal,useSynonym, duplcated detection)
				"RANK/DESC,DATE/DESC",  // set sort field (field,order) multi sort '/'
				"basic,rpfmo,100",  // set sort field (field,order) multi sort '/'
				"TITLE/100,CONTENT/50",// set search field
				"DOCID,LINK_ID,BD_ID,BOARD_NAME,TITLE,CONTENT,COMPANY_NAME,COMPANY_JOB,COMPANY_ADDR1,EMAIL,TEL_NO,WORK_KIND,WORK_TYPE,OFFER_TITLE,INSERT_DT,INSERT_ID,UPDATE_DT,UPDATE_ID,MENU_CD,OFFER_NAME,WORKPROJECT_ID,ALIAS",// set document field
				"", // set date range
				"", // set rank range
				"", // set prefix query, example: <fieldname:contains:value1>|<fieldname:contains:value2>/1,  (fieldname:contains:value) and ' ', or '|', not '!' / operator (AND:1, OR:0)
				"", // set collection query (<fieldname:contains:value^weight | value^weight>/option...) and ' ', or '|'
				"", // set boost query (<fieldname:contains:value> | <field3:contains:value>...) and ' ', or '|'
				"", // set filter operation (<fieldname:operator:value>)
				"", // set groupby field(field, count)
				"", // set sort field group(field/order,field/order,...)
				"", // set categoryBoost(fieldname,matchType,boostID,boostKeyword)
				"", // set categoryGroupBy (fieldname:value)
				"", // set categoryQuery (fieldname:value)
				"", // set property group (fieldname,min,max, groupcount)
				"", // use check prefix query filed
				"ALIAS", // set use check fast access field
				"", // set multigroupby field
				"", // set auth query (Auth Target Field, Auth Collection, Auth Reference Field, Authority Query)
				"", // set Duplicate Detection Criterion Field, RANK/DESC,DATE/DESC
				"일자리지원" // collection display name
			},
			{
				"miniJobMatchingNet", // set index name
				"miniJobMatchingNet", // set collection name
				"0,3",  // set pageinfo (start,count)
				"1,0,0,0,0", // set query analyzer (useKMA,isCase,useOriginal,useSynonym, duplcated detection)
				"RANK/DESC,DATE/DESC",  // set sort field (field,order) multi sort '/'
				"basic,rpfmo,100",  // set sort field (field,order) multi sort '/'
				"TITLE/100,CONTENT/50",// set search field
				"DOCID,LINK_ID,TITLE,CONTENT,COMPANY_NM,COMPANY_JOB,TEL_NO,INSERT_DT,INFOSUB_ID,KIND_NM,HIRE_CNT,PAY,EDUCATION,CAREER,MENU_CD,ALIAS",// set document field
				"", // set date range
				"", // set rank range
				"", // set prefix query, example: <fieldname:contains:value1>|<fieldname:contains:value2>/1,  (fieldname:contains:value) and ' ', or '|', not '!' / operator (AND:1, OR:0)
				"", // set collection query (<fieldname:contains:value^weight | value^weight>/option...) and ' ', or '|'
				"", // set boost query (<fieldname:contains:value> | <field3:contains:value>...) and ' ', or '|'
				"", // set filter operation (<fieldname:operator:value>)
				"", // set groupby field(field, count)
				"", // set sort field group(field/order,field/order,...)
				"", // set categoryBoost(fieldname,matchType,boostID,boostKeyword)
				"", // set categoryGroupBy (fieldname:value)
				"", // set categoryQuery (fieldname:value)
				"", // set property group (fieldname,min,max, groupcount)
				"", // use check prefix query filed
				"ALIAS", // set use check fast access field
				"", // set multigroupby field
				"", // set auth query (Auth Target Field, Auth Collection, Auth Reference Field, Authority Query)
				"", // set Duplicate Detection Criterion Field, RANK/DESC,DATE/DESC
				"미니잡매칭" // collection display name
			}
		};
	}
}