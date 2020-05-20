package com.cafe.menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBCPConn;

public class MenuDAO {
	
	public int insertMenu(MenuDTO dto) {
		int result = 0;
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO menu (menuNum, categoryNum, menuName,thumbnail, text, price) VALUES(menu_seq, ?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getCategoryNum());
			pstmt.setString(2, dto.getMenuName());
			pstmt.setString(3, dto.getThumbnail());
			pstmt.setString(4, dto.getText());
			pstmt.setInt(5, dto.getPrice());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return result;
	}
	
	public int dataCount() {
		int result=0;		Connection conn = DBCPConn.getConnection();

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql="SELECT NVL(COUNT(*), 0) FROM menu";
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next())
				result=rs.getInt(1);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return result;
	}
	
	public List<MenuDTO> listMenu(int offset, int rows, int categoryNum) {
		List<MenuDTO> list = new ArrayList<MenuDTO>();		Connection conn = DBCPConn.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT menuNum,categoryNum, menuName, thumbnail, text, price FROM menu WHERE categoryNum = ? ORDER BY menuNum DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNum);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				MenuDTO dto = new MenuDTO();
				dto.setMenuNum(rs.getInt("menuNum"));
				dto.setCategoryNum(rs.getInt("categoryNum"));
				dto.setMenuName(rs.getString("menuName"));
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setText(rs.getString("text"));
				dto.setPrice(rs.getInt("price"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return list;
	}
	
	public List<MenuDTO> listAllMenu(int offset, int rows) {
		List<MenuDTO> list = new ArrayList<MenuDTO>();		Connection conn = DBCPConn.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT menuNum,categoryNum, menuName, thumbnail, text, price FROM menu ORDER BY menuNum DESC OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, rows);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				MenuDTO dto = new MenuDTO();
				dto.setMenuNum(rs.getInt("menuNum"));
				dto.setCategoryNum(rs.getInt("categoryNum"));
				dto.setMenuName(rs.getString("menuName"));
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setText(rs.getString("text"));
				dto.setPrice(rs.getInt("price"));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return list;
	}
	
	public MenuDTO readPhoto(int menuNum) {
		MenuDTO dto=null;
		
		Connection conn = DBCPConn.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql;
		
		try {
			sql = "SELECT menuNum, categoryNum, menuName, thumbnail, text, price FROM menu WHERE menuNum=?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, menuNum);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new MenuDTO();
				dto.setMenuNum(rs.getInt("menuNum"));
				dto.setCategoryNum(rs.getInt("categoryNum"));
				dto.setMenuName(rs.getString("menuName"));
				dto.setThumbnail(rs.getString("thumbnail"));
				dto.setText(rs.getString("text"));
				dto.setPrice(rs.getInt("price"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
				
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return dto;
	}
	
	public int updateMenu(MenuDTO dto) {
		int result=0;		Connection conn = DBCPConn.getConnection();

		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "UPDATE menu SET categoryNum=?, menuName=?, thumbnail=?, text=?, price=?";
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getCategoryNum());
			pstmt.setString(2, dto.getMenuName());
			pstmt.setString(3, dto.getThumbnail());
			pstmt.setString(4, dto.getText());
			pstmt.setInt(5, dto.getPrice());
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return result;
	}
	
	public int deleteMenu(int menuNum) {
		int result=0;		Connection conn = DBCPConn.getConnection();

		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql="DELETE FROM menu WHERE menuNum=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, menuNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			try {
				if(!conn.isClosed()) {
					DBCPConn.close(conn);
				}
			} catch (Exception e2) {
			}
		}
		
		return result;
	}
	
}
