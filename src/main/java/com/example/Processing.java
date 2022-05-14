package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.infinispan.commons.marshall.JavaSerializationMarshaller;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;

import javax.sql.DataSource;

import org.apache.camel.Exchange;


public class Processing {
	
	private DataSource dataSource;
	private DataSource dataSource1; 
	   
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DataSource getDataSource1() {
		return dataSource1;
	}

	public void setDataSource1(DataSource dataSource1) {
		this.dataSource1 = dataSource1;
	}
	

	public void getBorrowerApplication(Exchange exchange) throws Exception{
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement getBorrowerList = null;
		connection = dataSource.getConnection();
		getBorrowerList = connection.prepareStatement("select distinct * from borrower_application_detailsaccepted where bid=? order by appdate desc;");
		getBorrowerList.setString(1, input.get("bid").toString());
		System.out.println("#####"+input.get("bid").toString());
		rs = getBorrowerList.executeQuery();
		List<Map<String,String>>resultlist = new ArrayList<Map<String,String>>();
		while (rs.next()) {
			Map<String,String>result = new HashMap<>();
			System.out.println("#####"+rs.getString("appid"));
			result.put("appid",rs.getString("appid"));
			result.put("bid",rs.getString("bid"));
			result.put("LoanAmnt",rs.getString("loanamtreq"));  
			result.put("mthlyincome",rs.getString("mthlyincome")); 
			result.put("bankaccountno",rs.getString("bankaccountno"));  
			result.put("ifsccode",rs.getString("ifsccode"));   
			result.put("bankname",rs.getString("bankname")); 
			result.put("branchname",rs.getString("branchname"));
			result.put("nationality",rs.getString("nationality")); 
			result.put("appdate",rs.getString("appdate")); 
			result.put("eligibility",rs.getString("eligibility")); 
			resultlist.add(result);
	        }
			rs.close();   
			connection.close(); 
		exchange.getIn().setBody(resultlist);
	}

	public void getBorrowerApplicationLoanExec(Exchange exchange) throws Exception{
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement getBorrowerList = null;
		connection = dataSource.getConnection();
		getBorrowerList = connection.prepareStatement("select distinct * from borrower_application_detailsaccepted where LoanExecId=? or LoanExecId is null order by appdate desc;");
		getBorrowerList.setString(1, input.get("execid").toString());
		System.out.println("#####"+input.get("execid").toString());
		rs = getBorrowerList.executeQuery();
		List<Map<String,String>>resultlist = new ArrayList<Map<String,String>>();
		while (rs.next()) {
			Map<String,String>result = new HashMap<>();
			System.out.println("#####"+rs.getString("appid"));
			result.put("appid",rs.getString("appid"));
			result.put("bid",rs.getString("bid"));
			result.put("LoanAmnt",rs.getString("loanamtreq"));  
			result.put("mthlyincome",rs.getString("mthlyincome")); 
			result.put("bankaccountno",rs.getString("bankaccountno"));  
			result.put("ifsccode",rs.getString("ifsccode"));   
			result.put("bankname",rs.getString("bankname")); 
			result.put("branchname",rs.getString("branchname"));
			result.put("nationality",rs.getString("nationality")); 
			result.put("appdate",rs.getString("appdate")); 
			result.put("eligibility",rs.getString("eligibility")); 
			resultlist.add(result);
	        }
			rs.close();  
			connection.close();  
		exchange.getIn().setBody(resultlist);
	}

	public void SubmitBorrowerApplicationEligible(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_Application_DetailsAccepted(BID,LoanAmtReq,MthlyIncome,BankAccountNo,IFSCCode,BankName,BranchName,Nationality,AppDate,Eligibility)"+
		"values(?,?,?,?,?,?,?,?,?,?) RETURNING appid;");
		//System.out.println("********"+input.get("loanamtreq").getClass());
		String loanAmnt=input.get("loanamtreq").toString();
		String mthlyincome=input.get("mthlyincome").toString();
		Boolean eligibility= new Boolean(input.get("eligibility").toString());
		//System.out.println("BID"+input.get("bid").toString());
		insertApplication.setString(1, input.get("bid").toString());
		insertApplication.setLong(2, Long.parseLong(loanAmnt.toString()));
		insertApplication.setLong(3, Long.parseLong(mthlyincome.toString()));
		insertApplication.setString(4, input.get("bankaccountno").toString());
		insertApplication.setString(5, input.get("ifsccode").toString());
		insertApplication.setString(6, input.get("bankname").toString());
		insertApplication.setString(7, input.get("branchname").toString());
		insertApplication.setString(8, input.get("nationality").toString());
		insertApplication.setDate(9, java.sql.Date.valueOf(input.get("appdate").toString()));
		insertApplication.setBoolean(10, true);
		connection.setAutoCommit(false);
		int appid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		appid = rs.getInt(1);
		}else{
		appid = insertApplication.getUpdateCount();
		}
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("appid", appid);
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result);  
	/*}
	catch(Exception e) {
	  Map<String,String>result = new HashMap<>();	
	  result.put("Status","Failed");
	  result.put("Messaage","Unable to insert data");
	  exchange.getIn().setBody(result);
	}*/
	}

	public void SubmitBorrowerApplicationNotEligible(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_Application_DetailsRejected(BID,LoanAmtReq,MthlyIncome,BankAccountNo,IFSCCode,BankName,BranchName,Nationality,AppDate,Eligibility)"+
		"values(?,?,?,?,?,?,?,?,?,?) RETURNING appid;");
		//System.out.println("********"+input.get("loanamtreq").getClass());
		String loanAmnt=input.get("loanamtreq").toString();
		String mthlyincome=input.get("mthlyincome").toString();
		Boolean eligibility= new Boolean(input.get("eligibility").toString());
		//System.out.println("BID"+input.get("bid").toString());
		insertApplication.setString(1, input.get("bid").toString());
		insertApplication.setLong(2, Long.parseLong(loanAmnt.toString()));
		insertApplication.setLong(3, Long.parseLong(mthlyincome.toString()));
		insertApplication.setString(4, input.get("bankaccountno").toString());
		insertApplication.setString(5, input.get("ifsccode").toString());
		insertApplication.setString(6, input.get("bankname").toString());
		insertApplication.setString(7, input.get("branchname").toString());
		insertApplication.setString(8, input.get("nationality").toString());
		insertApplication.setDate(9, java.sql.Date.valueOf(input.get("appdate").toString()));
		insertApplication.setBoolean(10, false);
		connection.setAutoCommit(false);
		int appid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		appid = rs.getInt(1);
		}else{
		appid = insertApplication.getUpdateCount();
		}
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("appid", appid);
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result);  
	/*}
	catch(Exception e) {
	  Map<String,String>result = new HashMap<>();	
	  result.put("Status","Failed");
	  result.put("Messaage","Unable to insert data");
	  exchange.getIn().setBody(result);
	}*/
	}

	public void UpdateBorrowerApplication(Exchange exchange) throws Exception{
		// try {
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		Boolean eligibility = new Boolean(input.get("eligibility").toString());
		int AppID = new Integer (input.get("appid").toString());
		String BID=input.get("bid").toString();
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("UPDATE Borrower_Application_Details SET eligibility=? where appid=? AND bid=? ;");
		insertApplication.setBoolean(1, eligibility);
		insertApplication.setInt(2, AppID);
		insertApplication.setString(3, BID);

		connection.setAutoCommit(false);
		int rowsAffected = insertApplication.executeUpdate();
		//System.out.println("response key"+rowsAffected);	
		Map<String,String>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("Messaage","Application updated in DB");
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result); 
	// }
	// catch(Exception e) {
	//   Map<String,String>result = new HashMap<>();	
	//   result.put("Status","Failed");
	//   result.put("Messaage","Unable to update data");
	//   exchange.getIn().setBody(result);
	// } 
	}
	
	public void insertBorrowerFeeDetails(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_Application_Details(BID,LoanAmtReq,MthlyIncome,BankAccountNo,IFSCCode,BankName,BranchName,Nationality,AppDate,Eligibility)"+
		"values(?,?,?,?,?,?,?,?,?,?) RETURNING appid;");
		//System.out.println("********"+input.get("loanamtreq").getClass());
		String loanAmnt=input.get("loanamtreq").toString();
		String mthlyincome=input.get("mthlyincome").toString();
		Boolean eligibility= new Boolean(input.get("eligibility").toString());
		//System.out.println("BID"+input.get("bid").toString());
		insertApplication.setString(1, input.get("bid").toString());
		insertApplication.setLong(2, Long.parseLong(loanAmnt.toString()));
		insertApplication.setLong(3, Long.parseLong(mthlyincome.toString()));
		insertApplication.setString(4, input.get("bankaccountno").toString());
		insertApplication.setString(5, input.get("ifsccode").toString());
		insertApplication.setString(6, input.get("bankname").toString());
		insertApplication.setString(7, input.get("branchname").toString());
		insertApplication.setString(8, input.get("nationality").toString());
		insertApplication.setDate(9, java.sql.Date.valueOf(input.get("appdate").toString()));
		insertApplication.setBoolean(10, eligibility);
		connection.setAutoCommit(false);
		int appid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		appid = rs.getInt(1);
		}else{
		appid = insertApplication.getUpdateCount();
		}
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("appid", appid);
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result);  
	/*}
	catch(Exception e) {
	  Map<String,String>result = new HashMap<>();	
	  result.put("Status","Failed");
	  result.put("Messaage","Unable to insert data");
	  exchange.getIn().setBody(result);
	}*/
	}

	private RemoteCacheManager cacheManager;
    private RemoteCache<String, Object> ifscCache;
    private String stateKey = "";
	 
    ConfigurationBuilder builder = new ConfigurationBuilder();
	
	public void getIFSCFromCache(Exchange exchange) throws SQLException{
		System.out.println("inside java method");
		stateKey=exchange.getIn().getHeader("key",String.class);
		String server=exchange.getIn().getHeader("hotrod_conn",String.class);
		String user=exchange.getIn().getHeader("hotrod_user",String.class);
		String pass=exchange.getIn().getHeader("hotrod_pass",String.class);
	builder.marshaller(new JavaSerializationMarshaller()).addJavaSerialWhiteList("java.util.", "java.util.HashMap")
				.addServers(server)
				.security().authentication().username(user)
				.password(pass);

		cacheManager = new RemoteCacheManager(builder.build());
	        ifscCache = cacheManager.getCache("IFSC");
	        if(!ifscCache.containsKey(stateKey)){		
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement getState = null;

                connection = dataSource1.getConnection();
				getState = connection.prepareStatement("select * from ifscdetails where ifsc=?");
				getState.setString(1, stateKey.toString());
		        rs = getState.executeQuery();
                System.out.println("after DB query is executed");
				while (rs.next()) {
                    System.out.println("from DB"+rs.getString("BankName"));
                    Map<String,String>ifsclist = new HashMap<>();
                    ifsclist.put("BankName",rs.getString("BankName"));
                    ifsclist.put("Branch",rs.getString("Branch"));
                    stateKey=rs.getString("IFSC");
                    ifscCache.put(stateKey, ifsclist);
	            }
				rs.close();
				connection.close();
				getState.close();
				Map<String,String> bankDetails =  (Map<String, String>) ifscCache.get(stateKey);
				exchange.getIn().setBody(bankDetails);
			}
			else{
			@SuppressWarnings("unchecked")
			Map<String,String> bankDetails =  (Map<String, String>) ifscCache.get(stateKey);
			System.out.println("Bank Details from cache :"+bankDetails);
			exchange.getIn().setBody(bankDetails);
			}
	}
	
	public void insertBorrowerLoanApprovalApprovedDetails(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_LoanApproved_Details(AppID ,BorrowerID ,LoanApproved ,LoanAmtApproved ,LoanTenureApproved ,LoanRate ,LoanApprovalOfficer )"+
		"values(?,?,?,?,?,?,?) RETURNING loanno;");
		int LoanTenureApproved=new Integer(input.get("loanTenureApproved").toString());
		String loanAmntApproved=input.get("loanAmtApproved").toString();
		float ratePer= new Float (input.get("loanRate").toString());
		//System.out.println("BID"+input.get("bid").toString());
		insertApplication.setString(1, input.get("appid").toString());
		insertApplication.setString(2, input.get("bid").toString());
		insertApplication.setBoolean(7, true);
		insertApplication.setLong(4, Long.parseLong(loanAmntApproved.toString()));
		insertApplication.setInt(5, LoanTenureApproved);
		insertApplication.setFloat(6, ratePer);
		insertApplication.setString(7, input.get("loanApprovalOfficer").toString());
		connection.setAutoCommit(false);
		int appid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		appid = rs.getInt(1);
		}else{
		appid = insertApplication.getUpdateCount();
		}
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("loanid", appid);
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result);  
	/*}
	catch(Exception e) {
	  Map<String,String>result = new HashMap<>();	
	  result.put("Status","Failed");
	  result.put("Messaage","Unable to insert data");
	  exchange.getIn().setBody(result);
	}*/
	}

	public void insertBorrowerLoanApprovalRejectedDetails(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_LoanApproved_Details(AppID ,BorrowerID ,LoanApproved ,LoanAmtApproved ,LoanTenureApproved ,LoanRate ,LoanApprovalOfficer )"+
		"values(?,?,?,?,?,?,?) RETURNING loanno;");
		int LoanTenureApproved=new Integer(input.get("loanTenureApproved").toString());
		String loanAmntApproved=input.get("loanAmtApproved").toString();
		float ratePer= new Float (input.get("loanRate").toString());
		//System.out.println("BID"+input.get("bid").toString());
		insertApplication.setString(1, input.get("appid").toString());
		insertApplication.setString(2, input.get("bid").toString());
		insertApplication.setBoolean(7, false);
		insertApplication.setLong(4, Long.parseLong(loanAmntApproved.toString()));
		insertApplication.setInt(5, LoanTenureApproved);
		insertApplication.setFloat(6, ratePer);
		insertApplication.setString(7, input.get("loanApprovalOfficer").toString());
		connection.setAutoCommit(false);
		int appid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		appid = rs.getInt(1);
		}else{
		appid = insertApplication.getUpdateCount();
		}
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("loanid", appid);
		insertApplication.close();
		connection.commit();
		connection.close();  
		exchange.getIn().setBody(result);  
	/*}
	catch(Exception e) {
	  Map<String,String>result = new HashMap<>();	
	  result.put("Status","Failed");
	  result.put("Messaage","Unable to insert data");
	  exchange.getIn().setBody(result);
	}*/
	}
}
