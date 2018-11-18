package com.test.lucene;


import java.io.File;
import java.io.FileFilter;

public class TextFileFilter implements FileFilter {

   @Override
   public boolean accept(File pathname) {
	  System.out.println(pathname.getName());
      return pathname.getName().toLowerCase().endsWith(".txt");
   }
}