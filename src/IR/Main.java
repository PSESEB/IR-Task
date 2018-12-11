package IR;


import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;


/**
 * Main Method with Params
 * 
 * @author weenzeal
 *
 */
public class Main {

	public static void main(String[] args) {
		
		//checking if all the arguments agree to the assignment description
		
		//check how many args given
		if(args.length != 4) {
			System.out.println("You need to parse exactly 4 Parameters! Try again");
			System.exit(0);
		}
		//name every arg
		String pathDocs = args[0];
		String pathIndex = args[1];
		String rankingModel = args[2];
		boolean modeVS = true;
		if(rankingModel.toUpperCase().equals("VS")) {
			modeVS = true;
			
		}
		else if(rankingModel.toUpperCase().equals("OK")){
			modeVS = false;
		}
		else {
			System.out.println("Mode needs to be either 'OK' or 'VS' ... Try again");
			System.exit(0);
		}
		
		String userquery = args[3];
		int max_search_terms = 10;
		//get instances of every file listed in the documents path
		ArrayList<Doc> documents = new ArrayList<Doc>();
	    for(String file : FileFactory.traverseFolderStructure(pathDocs)) {
	    	documents.add(FileFactory.getInstance(file));
	    }
	    
	    
	    try {
	    	//if index doesn't exists then create it
			if (!DirectoryReader.indexExists(FSDirectory.open(Paths.get(pathIndex)))) {
				
			   
				Indexer myindexer = new Indexer(pathIndex);
				myindexer.createIndex(documents);
				myindexer.close();
			}else {
				System.out.println("Index found! No reindexing of Documents.");
			}
			//if it exists we just input the path to our searcher method
			
			 // IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(pathIndex)));
			  
			  //reader.getTermFreqVectors(arg0)
			  //IndexSearcher searcher = new IndexSearcher(reader);
		      long startTime = System.currentTimeMillis();

		      //create a term to search file name
		     // Term term = new Term("title", userquery);
		      //create the term query object
		      //Query query = new FuzzyQuery(term);
		      
		      Searcher search = new Searcher(pathIndex);
		      
		      search.setSimilarity(modeVS ? new BM25Similarity() : new ClassicSimilarity());
		      
		      
		      TopDocs hits = search.search(userquery, max_search_terms);
		      long endTime = System.currentTimeMillis();

		      System.out.println(hits.totalHits +
		         " documents found. Time :" + (endTime - startTime) +" ms");
		      int i = 1;
		     
		      System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		      System.out.printf("%5s %20s %30s %50s", "RANK", "SCORE", "TITLE", "PATH");
		      System.out.println();
		      System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
		      for(ScoreDoc scoreDoc : hits.scoreDocs) {
		    	  
		         Document doc = search.getDocument(scoreDoc);
		         System.out.printf("%5d %20f %40s %50s",
		                 i++, scoreDoc.score, doc.get("title"), doc.get("path"));
		         System.out.println();
		        
		      }
		     
			
		
			
		} catch (Exception e1) { //in case there is no index File, in case topdocs doesn't function welll
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	   
	    
	    

	}

}
