// ToDoItemRepository.java
package com.libertymutual.goforcode.todolist.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.todolist.models.ToDoItem;

@Service
public class ToDoItemRepository {

	ArrayList<ToDoItem> items = new ArrayList<ToDoItem>();

	private int nextId = 1;

	/**
	 * Get all the items from the file.
	 * 
	 * @return A list of the items. If no items exist, returns an empty list.
	 */
	public List<ToDoItem> getAll() {

		items.clear();

		try (FileReader in = new FileReader("C:\\Users\\N0116605\\dev\\to-do-list\\items.csv");
				)
		{
			Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
			for (CSVRecord record : records) {
				ToDoItem newItem = new ToDoItem();
				int idToInt = Integer.parseInt(record.get(0));
				Boolean isCompleteAsBoolean = Boolean.valueOf(record.get(2));

				newItem.setId(idToInt);
				newItem.setText(record.get(1));
				newItem.setComplete(isCompleteAsBoolean);

				items.add(newItem);

			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	/**
	 * Assigns a new id to the ToDoItem and saves it to the file.
	 * 
	 * @param item
	 *            The to-do item to save to the file.
	 * @throws IOException
	 */
	public void create(ToDoItem item) throws IOException {

		String outputFile = "C:\\Users\\N0116605\\dev\\to-do-list\\items.csv";
		CSVFormat csvFileFormat = CSVFormat.EXCEL;
		FileWriter fileWriter = new FileWriter(outputFile, true);
		CSVPrinter csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

		String[] record = { Integer.toString(nextId), item.getText(), String.valueOf(false) };
		nextId += 1;

		try {
			csvFilePrinter.printRecord(record);

		} catch (IOException e) {
			System.out.println("This isn't working");
		}

		csvFilePrinter.close();

	}

	/**
	 * Gets a specific ToDoItem by its id.
	 * 
	 * @param id
	 *            The id of the ToDoItem.
	 * @return The ToDoItem with the specified id or null if none is found.
	 */

	public ToDoItem getById(int id) {

		for (ToDoItem getItemId : items) {
			if (getItemId.getId() == id) {
				return getItemId;
			}  
			System.out.println("something is wrong with update");
		} return null; 
	} 

	/**
	 * Updates the given to-do item in the file.
	 * 
	 * @param item
	 *            The item to update.
	 */
	public void update(ToDoItem item) {

		CSVFormat csvFileFormat = CSVFormat.EXCEL;
		try (FileWriter updateFile = new FileWriter("C:\\Users\\N0116605\\dev\\to-do-list\\items.csv");
				CSVPrinter csvFilePrinter = new CSVPrinter(updateFile, csvFileFormat))
		{	


			item.setComplete(true);

			for (ToDoItem updateItem : items) {

				String[] updateRecord = { Integer.toString(updateItem.getId()), updateItem.getText(), String.valueOf(updateItem.isComplete()) };

				csvFilePrinter.printRecord(updateRecord);

			}

			System.out.println("You didn't request an udpate");
			csvFilePrinter.close();	
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file");
		} catch (IOException e) {
			System.out.println("Return an Error");
			e.printStackTrace();
		}

	}
}