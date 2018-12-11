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
      
     
      
      
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      //create the indexer
      writer = new IndexWriter(indexDirectory, config);
   
   }

   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }

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

   private void indexFile(Doc datadoc) throws IOException {
      System.out.println("Indexing "+datadoc.getPath()); //do we want info to be printed in the terminal?
      Document document = getDocument(datadoc);
      writer.addDocument(document);
   }

   public int createIndex(ArrayList<Doc> docs) throws IOException {	   	   
	   for (Doc d : docs) {		   
            indexFile(d);
	   }

      return writer.numDocs();
   }
}