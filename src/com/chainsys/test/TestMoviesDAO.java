package com.chainsys.test;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import com.chainsys.jdbc.MovieValidator;
import com.chainsys.jdbc.Movies;
import com.chainsys.jdbc.MoviesDAO;

public class TestMoviesDAO {

	public static void main(String[] args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		MoviesDAO moviesDAO = new MoviesDAO();
		Movies movies = new Movies();
		MovieValidator movievalidate = new MovieValidator();
		System.out
				.println("1.Add,2.Update,3.Delete,4.FindById,5.FindAll,6.Movies Count,7.Copyright date");
		int input = scanner.nextInt();
		switch (input) {
		case 1:
			System.out
					.println("Enter name,price,date(YYYY-MM-DD),copyrightDate to add");
			System.out.println("Enter Movie Name");
			movies.name = scanner.next();
			System.out.println("Enter Movie Price");
			movies.price = scanner.nextInt();
			System.out.println("Enter Release Date");
			String date = scanner.next();
			movies.releaseDate = Date.valueOf(date);
			System.out.println("Movie Copyright Date");
			movies.copyrightDate = LocalDate.parse(scanner.next());
			try {
				movievalidate.validateAdd(movies);
				moviesDAO.addMovies(movies);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			System.out.println("Enter name and id to update");
			System.out.println("Enter Movie Name");
			movies.name = scanner.next();
			System.out.println("Enter Movie id");
			movies.id = scanner.nextInt();
			movievalidate = new MovieValidator();
			try {
				if (moviesDAO.findById(movies) != null) {
					movievalidate.validateUpdate(movies);
					moviesDAO.updateMovie(movies);
					System.out.println("Movie Name Updated Sucessfully");
				} else {
					System.out.println("No Record Found to Update");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			System.out.println("Enter movie id to Delete");
			System.out.println("Enter movie id");
			int id2 = scanner.nextInt();
			moviesDAO.deleteMovie(id2);
			System.out.println("Movie Deleted Sucessfully");
			break;
		case 4:
			System.out.println("Enter movie id to find");
			System.out.println("Enter Movie ID");
			movies.id = scanner.nextInt();
			Movies mov = moviesDAO.findById(movies);
			if (mov != null) {
				System.out.print("ID " + mov.id + " ");
				System.out.print("Name " + mov.name + " ");
				System.out.print("Price " + mov.price);
			} else {
				System.out.println("No Record");
			}
			break;
		case 5:
			ArrayList<Movies> movielist = moviesDAO.findAll();
			if (movielist.size() != 0 && !movielist.isEmpty())
				for (Movies mm : movielist) {
					System.out.println("Movie ID " + mm.id);
					System.out.println("Name " + mm.name);
					System.out.println("Price " + mm.price);
				}
			else {
				System.out.println("No Record Found");
			}
			break;
		case 6:

			System.out.println("Available Movies " + moviesDAO.moviesCount());
			break;
		case 7:
			System.out.println("Enter Copyright date to find Movie");
			String copydate = scanner.next();
			movies.copyrightDate = LocalDate.parse(copydate);
			Movies mov1 = moviesDAO.findByCopyright(movies);
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("E MMMM dd MM yyyy");
			if (mov1 != null) {
				System.out.print("ID " + mov1.id + " ");
				System.out.print("Name " + mov1.name + " ");
				System.out.print("Price " + mov1.price);
				String copy = formatter.format(mov1.copyrightDate);
				System.out.println("CopyRight Date :" + copy);
			} else {
				System.out.println("No Record");
			}
			break;
		default:
			break;
		}
		scanner.close();
	}

}
