package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.camel.Exchange;

public class Approver {
    private DataSource dataSource; 
	   
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
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
		insertApplication.execute();
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
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
		insertApplication.execute();
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
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
	public void insertBorrower_Fee_Document_Details(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		long ProcessingFeeAmt=new Long(input.get("processingFeeAmt"));
		System.out.println("Processing fee amount"+ProcessingFeeAmt);
		System.out.println("AppID: "+input.get("appid"));
		System.out.println("BorrowerID: "+input.get("bid"));
		System.out.println("LoanExecId: "+input.get("loanExecId"));
		System.out.println("InstanceID: "+input.get("instanceID"));
		System.out.println("before query"+input);
		System.out.println("query String: insert into Borrower_Fee_Document_Details(AppID ,BorrowerID ,ProcessingFeeAmt ,FeePaid ,LoanExecId ,PaymentExpired ,DocUploaded,InstanceID values("+input.get("appid")+","+input.get("bid")+","+ProcessingFeeAmt+","+false+","+input.get("loanExecId")+","+false+","+false+","+input.get("instanceID")+");");
		insertApplication = connection.prepareStatement("insert into Borrower_Fee_Document_Details(AppID ,BorrowerID ,ProcessingFeeAmt ,FeePaid ,LoanExecId ,PaymentExpired ,DocUploaded,InstanceID )"+
		"values(?,?,?,?,?,?,?,?)");
		insertApplication.setString(1, input.get("appid"));
		insertApplication.setString(2, input.get("bid"));
		insertApplication.setLong(3, ProcessingFeeAmt);
		insertApplication.setBoolean(4, false);
		insertApplication.setString(5, input.get("loanExecId"));
		insertApplication.setBoolean(6, false);
		insertApplication.setBoolean(7, false);
		insertApplication.setString(8, input.get("instanceID"));
		connection.setAutoCommit(false);
		insertApplication.execute();
		System.out.println("db querry executed");
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("Message", "Successfull");
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

	public void getBorrower_Fee_Document_Details(Exchange exchange) throws Exception{
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement getBorrowerList = null;
		connection = dataSource.getConnection();
		System.out.println("App id is"+input.get("appid").toString());
		getBorrowerList = connection.prepareStatement("select distinct * from Borrower_Fee_Document_Details where BorrowerID=? and AppID=?");
		getBorrowerList.setString(1, input.get("bid").toString());
		getBorrowerList.setString(2, input.get("appid").toString());
		rs = getBorrowerList.executeQuery();
		List<Map<String,String>>resultlist = new ArrayList<Map<String,String>>();
		while (rs.next()) {
			Map<String,String>result = new HashMap<>();
			System.out.println("#####"+rs.getString("appid"));
			result.put("appid",rs.getString("appid"));
			result.put("bid",rs.getString("BorrowerID"));
			result.put("ProcessingFeeAmt",rs.getString("ProcessingFeeAmt"));  
			result.put("FeePaid",rs.getString("FeePaid")); 
			result.put("LoanExecId",rs.getString("LoanExecId"));  
			result.put("PaymentExpired",rs.getString("PaymentExpired"));   
			result.put("InstanceID",rs.getString("InstanceID")); 
			result.put("DocUploaded",rs.getString("DocUploaded"));
			resultlist.add(result);
	        }
			rs.close(); 
			connection.close();   
		exchange.getIn().setBody(resultlist);
	}

	public void updateBorrower_Fee_Document_DetailsPaymentDone(Exchange exchange) throws Exception{
		// try {
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		String AppID = input.get("appid").toString();
		String BID=input.get("bid").toString();
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("UPDATE Borrower_Fee_Document_Details SET FeePaid=? where appid=? AND borrowerid=? ;");
		insertApplication.setBoolean(1, true);
		insertApplication.setString(2, AppID);
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

	public void updateBorrower_Fee_Document_DetailsPaymentExpired(Exchange exchange) throws Exception{
		// try {
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		String AppID = input.get("appid").toString();
		String BID=input.get("bid").toString();
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("UPDATE Borrower_Fee_Document_Details SET PaymentExpired=? where appid=? AND borrowerid=? ;");
		insertApplication.setBoolean(1, true);
		insertApplication.setString(2, AppID);
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

	public void updateBorrower_Fee_Document_DetailsUploadDocuments(Exchange exchange) throws Exception{
		// try {
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		String AppID = input.get("appid").toString();
		String BID=input.get("bid").toString();
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("UPDATE Borrower_Fee_Document_Details SET DocUploaded=? where appid=? AND borrowerid=? ;");
		insertApplication.setBoolean(1, true);
		insertApplication.setString(2, AppID);
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

	public void insertBorrower_RiskAssessment_Verification(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
                Connection connection = null;
                ResultSet rs = null;
                connection = dataSource.getConnection();
                PreparedStatement insertApplication=null;
		int cibil=new Integer(input.get("cibil").toString());
                int numberOfDefaults=new Integer(input.get("noOfDefaults").toString());
                boolean verify= new Boolean(input.get("verification").toString());
		System.out.println("input: "+input);
		System.out.println("App id: "+input.get("appid"));
		System.out.println("cibil: "+cibil);
		System.out.println("BorrowerID: "+input.get("bid"));
		System.out.println("No of defaults: "+numberOfDefaults);
		System.out.println("InstanceID: "+input.get("instanceID"));
		System.out.println("RiskValue: "+input.get("riskValue"));
		System.out.println("Verification: "+verify);
		System.out.println("Loan Exec ID: "+input.get("loanExecId"));
		System.out.println("before query"+input);
                System.out.println("insert into Borrower_RiskAssessment_Verification(AppID, BorrowerID , CIBIL, NoOfDefaults, RiskValue, Verification, LoanExecId,InstanceId) values ("+input.get("appid")+","+input.get("bid")+","+cibil+","+numberOfDefaults+","+input.get("riskValue")+","+verify+","+input.get("loanExecId")+","+input.get("instanceID")+");");
                insertApplication = connection.prepareStatement("insert into Borrower_RiskAssessment_Verification(AppID, BorrowerID , CIBIL, NoOfDefaults, RiskValue, Verification, LoanExecId,InstanceId) "+
                "values(?,?,?,?,?,?,?,?) RETURNING assesmentid;");
                insertApplication.setString(1, input.get("appid"));
                insertApplication.setString(2, input.get("bid"));
                insertApplication.setInt(3, cibil);
                insertApplication.setInt(4, numberOfDefaults);
                insertApplication.setString(5, input.get("riskValue"));
                insertApplication.setBoolean(6, verify);
                insertApplication.setString(7, input.get("loanExecId"));
                insertApplication.setString(8, input.get("instanceID"));
                connection.setAutoCommit(false);
                insertApplication.execute();
                System.out.println("db querry executed");
                Map<String,Object>result = new HashMap<>();
                result.put("Status","Success");
                result.put("Message", "Successfull");
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

	public void getBorrower_RiskAssessment_Verification(Exchange exchange) throws Exception{
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
                Connection connection = null;
                ResultSet rs = null;
                PreparedStatement getBorrowerList = null;
                connection = dataSource.getConnection();
                System.out.println("App id is"+input.get("appid").toString());
                getBorrowerList = connection.prepareStatement("select distinct * from Borrower_RiskAssessment_Verification where BorrowerID=? and AppID=?");
                getBorrowerList.setString(1, input.get("bid").toString());
                getBorrowerList.setString(2, input.get("appid").toString());
                rs = getBorrowerList.executeQuery();
                List<Map<String,String>>resultlist = new ArrayList<Map<String,String>>();
                while (rs.next()) {
                        Map<String,String>result = new HashMap<>();
                        System.out.println("#####"+rs.getString("appid"));
                        result.put("appid",rs.getString("appid"));
                        result.put("bid",rs.getString("BorrowerID"));
                        result.put("cibil",rs.getString("CIBIL"));
                        result.put("noOfDefaults",rs.getString("NoOfDefaults"));
                        result.put("riskValue",rs.getString("RiskValue"));
                        result.put("verification",rs.getString("Verification"));
                        result.put("InstanceID",rs.getString("InstanceID"));
                        result.put("loanExecID",rs.getString("LoanExecId"));
                        resultlist.add(result);
                }
                        rs.close();
                        connection.close();
                exchange.getIn().setBody(resultlist);
	}

	public void updateBorrower_RiskAssessment_Verification(Exchange exchange) throws Exception{
		// try {
		Map<String,Object>input = exchange.getIn().getBody(Map.class);
		String AppID = input.get("appid").toString();
		String BID=input.get("bid").toString();
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("UPDATE Borrower_RiskAssessment_Verification SET DocUploaded=? where appid=? AND bid=? ;");
		insertApplication.setBoolean(1, true);
		insertApplication.setString(2, AppID);
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
	
	public void getCIBILScore(Exchange exchange) throws Exception{
		int min=300;
		int max=900;
		int cibil = (int)(Math.random() * (max - min + 1) + min);
		Map<String,String>result = new HashMap<>();	
		result.put("CIBILScore",String.valueOf(cibil));
		exchange.getIn().setBody(cibil);
	}

	public void insertBorrower_LoanApproved_DetailsApproved(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		System.out.println("body before request"+input);
		insertApplication = connection.prepareStatement("insert into Borrower_LoanApproved_Details(AppID ,BorrowerID ,LoanApproved ,LoanAmtApproved ,LoanTenureApproved ,LoanRate ,LoanApprovalOfficer) "+
		"values(?,?,?,?,?,?,?) RETURNING loanno;");
		long loanAmtApproved=new Long(input.get("loanAmtApproved"));
		int loanTenureApproved=new Integer(input.get("LoanTenureApproved"));
		float loanRate= new Float(input.get("loanRate"));
		insertApplication.setString(1, input.get("appid"));
		insertApplication.setString(2, input.get("bid"));
		insertApplication.setBoolean(3, true);
		insertApplication.setLong(4, loanAmtApproved);
		insertApplication.setInt(5, loanTenureApproved);
		insertApplication.setFloat(6, loanRate);
		insertApplication.setString(7, input.get("loanExecId"));
		connection.setAutoCommit(false);
		int assesmentid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		assesmentid = rs.getInt(1);
		}else{
			assesmentid = insertApplication.getUpdateCount();
		}
		
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("Message", "Inserted in database");
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

	public void insertBorrower_LoanApproved_DetailsRejected(Exchange exchange) throws Exception{
		//try {
		Map<String,String>input = exchange.getIn().getBody(Map.class);
		Connection connection = null;
		ResultSet rs = null;
		connection = dataSource.getConnection();
		PreparedStatement insertApplication=null;
		insertApplication = connection.prepareStatement("insert into Borrower_LoanApproved_Details(AppID ,BorrowerID ,LoanApproved ,LoanAmtApproved ,LoanTenureApproved ,LoanRate ,LoanApprovalOfficer )"+
		"values(?,?,?,?,?,?,?) RETURNING assesmentid;");
		long loanAmtApproved=new Long(input.get("loanAmtApproved").toString());
		int loanTenureApproved=new Integer(input.get("LoanTenureApproved").toString());
		float loanRate= new Float(input.get("loanRate").toString());
		insertApplication.setString(1, input.get("appid").toString());
		insertApplication.setString(2, input.get("bid").toString());
		insertApplication.setBoolean(3, false);
		insertApplication.setLong(4, loanAmtApproved);
		insertApplication.setInt(5, loanTenureApproved);
		insertApplication.setFloat(6, loanRate);
		insertApplication.setString(7, input.get("loanExecId").toString());
		connection.setAutoCommit(false);
		int assesmentid=0;
		if (insertApplication.execute()) {
		rs = insertApplication.getResultSet();
		rs.next();
		assesmentid = rs.getInt(1);
		}else{
			assesmentid = insertApplication.getUpdateCount();
		}
		
		Map<String,Object>result = new HashMap<>();	
		result.put("Status","Success");
		result.put("Message", "Inserted in database");
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
