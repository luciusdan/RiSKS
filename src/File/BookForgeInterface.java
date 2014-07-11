/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import Data.Book;
import Exceptions.InvalidDateFileException;
import Exceptions.NoDataFileException;
import java.io.IOException;

/**
 *
 * @author Dirk
 */
public interface BookForgeInterface {
    public void readBook(Book book, boolean deleteWrong) throws InvalidDateFileException, NoDataFileException ;
    public void readFrom(Book book, Object obj) throws IOException ;
    public void writeBook(Book book) throws IOException ;
    public void writeTo (Book book, Object obj) throws IOException ;
}
