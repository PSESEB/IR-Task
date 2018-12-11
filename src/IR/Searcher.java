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


public class Searcher {
	
	   IndexSearcher indexSearcher;
	   MultiFieldQueryParser queryParser;
	   

	
	   public Searcher(String indexDirectoryPath) throws IOException {
	      IndexReader indexDirectory = DirectoryReader.open(FSDirectory.open(Paths.get(indexDirectoryPath)));
	      this.indexSearcher = new IndexSearcher(indexDirectory);
	      this.queryParser = new MultiFieldQueryParser(new String[] {"content", "title"},new EnglishAnalyzer());
	   }

	   public TopDocs search( String searchQuery, int max_terms) throws IOException, org.apache.lucene.queryparser.classic.ParseException {
	    
		Query query = this.queryParser.parse(searchQuery);
	      return indexSearcher.search(query, max_terms);
	   }
	   
	   public TopDocs search(Query query,Sort sort,  int max_terms) throws IOException, ParserException {
			return this.indexSearcher.search(query, max_terms,sort);
	   }
	   
	   public void setSimilarity(Similarity sim) {
		   this.indexSearcher.setSimilarity(sim);
	   }

	   public Document getDocument(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
	      return indexSearcher.doc(scoreDoc.doc);	
	   }

	
}
