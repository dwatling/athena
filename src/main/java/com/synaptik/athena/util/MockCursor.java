/**
 * Copyright 2010 Synaptik Solutions
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 * @author Dan Watling <dan@synaptik.com>
 */
package com.synaptik.athena.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/**
 * Useful for when you want to test methods that accept
 * a cursor.
 * 
 * All you have to do is initialize a new MockCursor then
 * call 'addRow' with the data you want. 
 * 
 * @author Dan Watling
 *
 */
public class MockCursor implements Cursor {

	List<List<Object>> data;
	int currentRow = -1;
	
	public MockCursor() {
		data = new ArrayList<List<Object>>();
		currentRow = -1;
	}
	
	public void addRow(Object... row) {
		addRow(Arrays.asList(row));
	}
	public void addRow(List<Object> row) {
		data.add(row);
	}
	
	public void close() {
		data.clear();
		data = null;
	}

	public void copyStringToBuffer(int arg0, CharArrayBuffer arg1) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public void deactivate() {
		close();
	}

	public byte[] getBlob(int arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public int getColumnCount() {
		return data.get(currentRow).size();
	}

	public int getColumnIndex(String arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public int getColumnIndexOrThrow(String arg0)
			throws IllegalArgumentException {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public String getColumnName(int arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public String[] getColumnNames() {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public int getCount() {
		return data.size();
	}

	public double getDouble(int arg0) {
		return ((Double)data.get(currentRow).get(arg0)).doubleValue();
	}

	public Bundle getExtras() {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public float getFloat(int arg0) {
		return ((Float)data.get(currentRow).get(arg0)).floatValue();
	}

	public int getInt(int arg0) {
		return ((Integer)data.get(currentRow).get(arg0)).intValue();
	}

	public long getLong(int arg0) {
		return ((Long)data.get(currentRow).get(arg0)).longValue();
	}

	public int getPosition() {
		return currentRow;
	}

	public short getShort(int arg0) {
		return ((Short)data.get(currentRow).get(arg0)).shortValue();
	}

	public String getString(int arg0) {
		return ((String)data.get(currentRow).get(arg0));
	}

	public boolean getWantsAllOnMoveCalls() {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public boolean isAfterLast() {
		return currentRow >= data.size();
	}

	public boolean isBeforeFirst() {
		return currentRow < 0;
	}

	public boolean isClosed() {
		return data == null;
	}

	public boolean isFirst() {
		return currentRow == 0;
	}

	public boolean isLast() {
		return currentRow == (data.size()-1);
	}

	public boolean isNull(int arg0) {
		return ((Object)data.get(currentRow).get(arg0)) == null;
	}

	public boolean move(int arg0) {
		currentRow += arg0;
		return !(isAfterLast() || isBeforeFirst());
	}

	public boolean moveToFirst() {
		currentRow = 0;
		return true;
	}

	public boolean moveToLast() {
		currentRow = data.size()-1;
		return true;
	}

	public boolean moveToNext() {
		currentRow ++;
		return !isAfterLast();
	}

	public boolean moveToPosition(int arg0) {
		currentRow = arg0;
		return !(isAfterLast() || isBeforeFirst());
	}

	public boolean moveToPrevious() {
		currentRow --;
		return !isBeforeFirst();
	}

	public void registerContentObserver(ContentObserver arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public void registerDataSetObserver(DataSetObserver arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public boolean requery() {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public Bundle respond(Bundle arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public void setNotificationUri(ContentResolver arg0, Uri arg1) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public void unregisterContentObserver(ContentObserver arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}

	public void unregisterDataSetObserver(DataSetObserver arg0) {
		throw new UnsupportedOperationException("Needs to be implemented.");
	}
}
