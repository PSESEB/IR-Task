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



public class Indexer {

   private IndexWriter writer;

   
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
   //function created for closing the index writer
   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }
   //function to include information and content of every file in the indexing
   //and then return a document with all requested information	
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
   //function to add the documents in the writer
   private void indexFile(Doc datadoc) throws IOException {
      System.out.println("Indexing "+datadoc.getPath()); 
      Document document = getDocument(datadoc); //get document function builds the appropriate file for the indexing
      writer.addDocument(document);
   }
   //and now getting the documents as an arraz and using the function indexFile to create the index
   public int createIndex(ArrayList<Doc> docs) throws IOException {	   	   
	   for (Doc d : docs) {		   
            indexFile(d);
	   }

      return writer.numDocs();
   }
}
