package com.lp.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.protocol.Resultset;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		Connection con = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/logindb?useSSL=false","root","12345AZERTazert");
			PreparedStatement pst = con.prepareStatement("insert into users(uname,upwd,uemail,umobile) values(?,?,?,?)");
			pst.setString(1, upwd);
			pst.setString(2, uemail);
			
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				session.setAttribute("name", rs.getString("uname"));
				dispatcher = request.getRequestDispatcher("index.jsp");
			} else {
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
