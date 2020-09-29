package com.blank.consumerapp.utils.rx

import android.database.Cursor

class RxCursorIterable(private val mIterableCursor: Cursor) : Iterable<Cursor> {
    override fun iterator(): Iterator<Cursor> =
        RxCursorIterator(mIterableCursor)

    private class RxCursorIterator(private val mCursor: Cursor) :
        Iterator<Cursor> {
        override fun hasNext(): Boolean = !mCursor.isClosed && mCursor.moveToNext()
        override fun next(): Cursor = mCursor
    }
}