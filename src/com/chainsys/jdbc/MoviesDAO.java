package com.chainsys.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class MoviesDAO {

	/**
	 * Method to ADD Movies
	 * 
	 * @param movie
	 * @throws Exception
	 */
	public void addMovies(Movies movie) throws Exception {
		try {
			Connection connection = ConnectionUtil.getConnection();
			String sql = "Insert into movies(id,name,price,releasedate,copyrightdate,createddatetime) values(movies_id_seq.NEXTVAL,?,?,?,?,?)";
			PreparedStatement preparedstatement = connection
					.prepareStatement(sql);
			preparedstatement.setString(1, movie.name);
			preparedstatement.setInt(2, movie.price);
			Date d = new Date(movie.releaseDate.getDate());
			preparedstatement.setDate(3, d);
			preparedstatement.setDate(4, Date.valueOf(movie.copyrightDate));
			LocalDateTime dd = LocalDateTime.now();
			Timestamp timestamp = Timestamp.valueOf(dd);
			preparedstatement.setTimestamp(5, timestamp);
			int row = preparedstatement.executeUpdate();
			System.out.println("Row Inserted " + row);
			ConnectionUtil.close(connection, preparedstatement, null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Unable to insert movies");
		}
	}

	/**
	 * Method to Update Movies
	 * 
	 * @param name
	 * @param price
	 * @param id
	 * @throws Exception
	 */
	public void updateMovie(Movies movies) throws Exception {
		try {
			Connection connection = ConnectionUtil.getConnection();
			String sql = "UPDATE movies set name=? where id=?";
			PreparedStatement preparedstatement = connection
					.prepareStatement(sql);
			preparedstatement.setString(1, movies.name);
			preparedstatement.setInt(2, movies.id);
			preparedstatement.executeUpdate();
			ConnectionUtil.close(connection, preparedstatement, null);
		} catch (SQLException e) {
			throw new Exception("Unable to update movie name");
		}

	}

	/**
	 * Method to Delete Movie
	 * 
	 * @param id
	 * @throws Exception
	 * @throws SQLException
	 */
	public void deleteMovie(int id) throws Exception {
		try {
			Connection connection = ConnectionUtil.getConnection();
			String sqldelete = "Delete from movies where id=?";
			PreparedStatement preparedstatement = connection
					.prepareStatement(sqldelete);
			preparedstatement.setInt(1, id);
			int row = preparedstatement.executeUpdate();
			System.out.println("Delete Movie sucessfully :" + row);
			ConnectionUtil.close(connection, preparedstatement, null);
		} catch (SQLException e) {
			throw new Exception("Unable to Delete movie");
		}
	}

	/**
	 * Method to Find Movie by Id
	 * 
	 * @param movie
	 * @return
	 * @throws SQLException
	 */
	public Movies findById(Movies movie) throws SQLException {
		Movies movieobj = null;
		Connection connection = ConnectionUtil.getConnection();
		String findsql = "Select id,name,price,releasedate,copyrightdate from movies where id=?";
		PreparedStatement preparedstatement = connection
				.prepareStatement(findsql);
		preparedstatement.setInt(1, movie.id);
		ResultSet rset = preparedstatement.executeQuery();

		if (rset.next()) {
			movieobj = new Movies();
			System.out.println("----");
			movieobj.id = rset.getInt("id");
			movieobj.name = rset.getString("name");
			movieobj.price = rset.getInt("price");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			movieobj.releaseDate = rset.getDate("releasedate");
			String date = format.format(rset.getDate("releasedate"));

			System.out.println(date);
			movieobj.copyrightDate = rset.getDate("copyrightdate")
					.toLocalDate();
		}
		ConnectionUtil.close(connection, preparedstatement, rset);
		return movieobj;
	}

	/**
	 * Method to find movies by its Copyright Date
	 * 
	 * @param movie
	 * @return
	 * @throws SQLException
	 */
	public Movies findByCopyright(Movies movie) throws SQLException {
		Movies movieobj = null;
		Connection connection = ConnectionUtil.getConnection();
		String findsql = "Select id,name,price,releasedate,copyrightdate from movies where copyrightdate=?";
		PreparedStatement preparedstatement = connection
				.prepareStatement(findsql);
		preparedstatement.setDate(1, Date.valueOf(movie.copyrightDate));
		ResultSet rset = preparedstatement.executeQuery();

		if (rset.next()) {
			movieobj = new Movies();
			System.out.println("----");
			movieobj.id = rset.getInt("id");
			movieobj.name = rset.getString("name");
			movieobj.price = rset.getInt("price");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			movieobj.releaseDate = rset.getDate("releasedate");
			String date = format.format(rset.getDate("releasedate"));

			System.out.println(date);
			movieobj.copyrightDate = rset.getDate("copyrightdate")
					.toLocalDate();
		}
		ConnectionUtil.close(connection, preparedstatement, rset);
		return movieobj;
	}

	/**
	 * Method to view all Movies
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Movies> findAll() throws SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String sqlselect = "select id,name,price from movies";
		PreparedStatement preparedstatement = connection
				.prepareStatement(sqlselect);
		ResultSet resultset = preparedstatement.executeQuery();
		System.out.println("---Movies---");
		ArrayList<Movies> movieList = new ArrayList<Movies>();
		while (resultset.next()) {
			Movies movie = new Movies();
			movie.id = resultset.getInt("id");
			movie.name = resultset.getString("name");
			movie.price = resultset.getInt("price");

			movieList.add(movie);
		}
		ConnectionUtil.close(connection, preparedstatement, resultset);
		return movieList;
	}

	/**
	 * Method to View overall movies Available
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int moviesCount() throws SQLException {
		Connection connection = ConnectionUtil.getConnection();
		String sqlselect = "select count(id) from movies";
		PreparedStatement preparedstatement = connection
				.prepareStatement(sqlselect);
		ResultSet resultset = preparedstatement.executeQuery();
		System.out.println("---Movies---");
		int count = 0;
		while (resultset.next()) {
			count++;
		}
		ConnectionUtil.close(connection, preparedstatement, resultset);
		return count;
	}

}
