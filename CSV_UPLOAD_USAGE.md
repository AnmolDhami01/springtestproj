# CSV Upload for Bulk Book Creation

## Endpoint

`POST /books/v1/addBookBulkCsv`

## Request Format

Upload a CSV file using multipart form data with the parameter name `file`.

## CSV Format

The CSV file should have the following structure:

### Header Row (Optional)

The first row is treated as a header and will be skipped.

### Data Rows

Each row should contain exactly 3 columns in this order:

1. **Book Name** (String)
2. **Price** (Number)
3. **Author Name** (String) - Must match an existing user's name

### Example CSV Content:

```csv
book_name,price,author_name
The Great Gatsby,25.99,John Doe
To Kill a Mockingbird,19.99,Jane Smith
1984,15.50,John Doe
```

## Validation Rules

1. **File Format**: Must be a `.csv` file
2. **File Size**: Cannot be empty
3. **Columns**: Must have exactly 3 columns (book_name, price, author_name)
4. **Book Names**: Must be unique (not already exist in database)
5. **Prices**: Must be valid numbers
6. **Authors**: Must match existing user names in the database
7. **Required Fields**: All fields must be non-empty

## Response Codes

- **200**: All books added successfully
- **207**: Some books added successfully (partial success)
- **400**: Bad request (invalid file format, missing data, or no valid books)
- **500**: Internal server error

## Example cURL Request

```bash
curl -X POST http://localhost:8080/books/v1/addBookBulkCsv \
  -F "file=@books.csv"
```

## Example HTML Form

```html
<form
  action="/books/v1/addBookBulkCsv"
  method="post"
  enctype="multipart/form-data"
>
  <input type="file" name="file" accept=".csv" required />
  <button type="submit">Upload CSV</button>
</form>
```

## Error Handling

The API provides detailed error messages for:

- Invalid file format
- Missing or empty required fields
- Invalid price format
- Non-existent authors
- Duplicate book names
- Insufficient data in rows

Each error message includes the line number for easy identification.

## Sample Error Response

```json
{
  "statusDescriptions": {
    "statusCode": 207,
    "statusDescription": "Some books added successfully. Errors: Line 3: User with name 'Unknown Author' not found; Line 5: Invalid price format 'abc'. Expected a number."
  },
  "data": null
}
```

## Notes

- The first row is automatically treated as a header and skipped
- Empty lines are ignored
- Books are only saved if all validation passes
- Partial success is reported when some books are valid and others are not
- All valid books are saved in a single transaction
