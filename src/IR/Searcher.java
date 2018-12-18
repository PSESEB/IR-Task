package IR;


import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.xml.ParserException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;

/**
 * 
 * Searcher to perform Queries on Indices
 * @author Theodora
 *
 */
public class Searcher {
	   //declaring globally the indexsearcher and the multifieldqueryparser
	   IndexSearcher indexSearcher;
	   MultiFieldQueryParser queryParser;
	   
	   /**
	    * Constructor loads index saved at given path
	    * @param indexDirectoryPath
	    * @throws IOException
	    */
	   public Searcher(String indexDirectoryPath) throws IOException {
	      //using the index directory path to create the index reader and the index searcher
	      IndexReader indexDirectory = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
	      this.indexSearcher = new IndexSearcher(indexDirectory);
	      // in order to look for both the title and the content in our search results we use the multifield query parser
	      this.queryParser = new MultiFieldQueryParser(new String[] {"content", "title"},new EnglishAnalyzer());
	   }

	   /**
	    * Query queryterm
	    * @param searchQuery
	    * @param max_terms
	    * @return TopDocs
	    * @throws IOException
	    * @throws org.apache.lucene.queryparser.classic.ParseException
	    */
	   public TopDocs search( String searchQuery, int max_terms) throws IOException, org.apache.lucene.queryparser.classic.ParseException {
	      //this topdocs function will return the most relevant documents filter by the maximun number of terms
	      Query query = this.queryParser.parse(searchQuery);
	      return indexSearcher.search(query, max_terms);
	   }
	   
	   /**
	    * Query queryterm with sort option
	    * @param query
	    * @param sort
	    * @param max_terms
	    * @return TopDocs
	    * @throws IOException
	    * @throws ParserException
	    */
	   public TopDocs search(Query query,Sort sort,  int max_terms) throws IOException, ParserException {
		   //this topdocs function will return the most relevant documents filter by the maximun number of terms and the sorting method
		   return this.indexSearcher.search(query, max_terms,sort);
	   }
	   
	   /**
	    * Change Similiarity of Searcher.
	    * Influences the score and thus the ranking of documents.
	    * @param sim Similarity that should be used for scoring documents
	    */
	   public void setSimilarity(Similarity sim) {
		   //similarity needed to be set for making use of the ranking model in our main method
		   this.indexSearcher.setSimilarity(sim);
	   }
       
	   /**
	    * Method to access scored Documents
	    * @param scoreDoc
	    * @return Document
	    * @throws CorruptIndexException
	    * @throws IOException
	    */
	   public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
		   //accessing the private method doc of indexsearcher for returning the most relevant document based on score
	      return indexSearcher.doc(scoreDoc.doc);	
	   }

	
}
