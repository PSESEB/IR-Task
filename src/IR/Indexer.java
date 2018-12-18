package IR;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.analysis.en.EnglishAnalyzer;


/**
 * Build and write into Lucene Indices
 * 
 * @author Theodora
 *
 */
public class Indexer {

	
   private IndexWriter writer;

   /**
    * Constructor creates Index in given Path
    * @param indexDirectoryPath Path where Index should be stored
    * @throws IOException
    */
   public Indexer(String indexDirectoryPath) throws IOException { //we create the path of the index
      //this directory will contain the indexes	   
      Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
      //Builds an analyzer with the default stop words
      EnglishAnalyzer analyzer = new EnglishAnalyzer();
      //we are using EnglishAnalyzer because it contains by default the PorterStemFilter
      
      
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      //create the indexer
      writer = new IndexWriter(indexDirectory, config);
   
   }
   
   /**
    * function created for closing the index writer
    * @throws CorruptIndexException
    * @throws IOException
    */
   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }
  
   /**
    * Create lucene Document from Doc Objects
    * @param datadoc
    * @return Document lucene document with all relevant information
    * @throws IOException
    */
   private Document getDocument(Doc datadoc) throws IOException {
      Document document = new Document();

     
      //index file contents
      Field contentField = new TextField("content",datadoc.getContent(),Field.Store.YES);
   
      //index file name
      
      Field fileTitleField = new TextField("title", datadoc.getTitle(),Field.Store.YES);
      
      //index file path
      Field filePathField = new StringField("path",datadoc.getPath(),Field.Store.YES );

      document.add(contentField);
      document.add(fileTitleField);
      document.add(filePathField);

      return document;
   }   
 
   /**
    * add single Document to Index
    * @param datadoc
    * @throws IOException
    */
   private void indexFile(Doc datadoc) throws IOException {
      System.out.println("Indexing "+datadoc.getPath()); 
      Document document = getDocument(datadoc); //get document function builds the appropriate file for the indexing
      writer.addDocument(document);
   }
   
  
   /**
    * Index multiple Docs as an array and using the function indexFile to create the index
    * @param docs
    * @return Size of Index
    * @throws IOException
    */
   public int createIndex(ArrayList<Doc> docs) throws IOException {	   	   
	   for (Doc d : docs) {		   
            indexFile(d);
	   }

      return writer.numDocs();
   }
}
