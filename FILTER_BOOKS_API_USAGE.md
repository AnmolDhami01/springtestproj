# Filter Books API Usage

## Endpoint

`POST /books/v1/filterBooks`

## Request Format

The API accepts a JSON object with optional filter parameters.

### Request Body

```json
{
  "name": "Book Name",
  "price": 100,
  "authorId": 1,
  "createdAt": "2025-07-09",
  "updatedAt": "2025-07-09T14:30:00",
  "pageNo": 0,
  "pageSize": 10
}
```

## Supported Date Formats

The `createdAt` and `updatedAt` fields now support multiple date formats:

### 1. Date-only format (recommended for date filtering)

```
"2025-07-09"
```

This will be interpreted as `2025-07-09T00:00:00`

### 2. Full datetime format with 'T' separator

```
"2025-07-09T14:30:00"
```

### 3. Full datetime format with space separator

```
"2025-07-09 14:30:00"
```

## Filter Parameters

All parameters are optional:

- **name**: String - Book name (supports partial matching, case-insensitive)
- **price**: Long - Exact price match
- **authorId**: Long - Author ID
- **createdAt**: String - Creation date (supports multiple formats)
- **updatedAt**: String - Last update date (supports multiple formats)
- **pageNo**: Integer - Page number (0-based, default: 0)
- **pageSize**: Integer - Number of items per page (default: 10)

## Example Requests

### Filter by date only

```json
{
  "createdAt": "2025-07-09",
  "pageNo": 0,
  "pageSize": 5
}
```

### Filter by name and price

```json
{
  "name": "Gatsby",
  "price": 25,
  "pageNo": 0,
  "pageSize": 10
}
```

### Filter by author and date range

```json
{
  "authorId": 1,
  "createdAt": "2025-01-01",
  "updatedAt": "2025-12-31",
  "pageNo": 0,
  "pageSize": 20
}
```

## Response Format

```json
{
  "statusDescriptions": {
    "statusCode": 200,
    "statusDescription": "Books filtered successfully"
  },
  "booksPage": {
    "content": [
      {
        "id": 1,
        "name": "The Great Gatsby",
        "price": 25,
        "author": {
          "id": 1,
          "name": "John Doe"
        },
        "createdAt": "2025-07-09T10:30:00",
        "updatedAt": "2025-07-09T10:30:00"
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10
    },
    "totalElements": 1,
    "totalPages": 1
  }
}
```

## Error Handling

The API now properly handles date parsing errors with clear error messages:

- **400**: Bad request (invalid date format)
- **500**: Internal server error

### Date Format Error Example

```json
{
  "statusDescriptions": {
    "statusCode": 500,
    "statusDescription": "Internal Server Error: Unable to parse date: invalid-date. Supported formats: yyyy-MM-dd, yyyy-MM-dd'T'HH:mm:ss, yyyy-MM-dd HH:mm:ss"
  }
}
```

## cURL Example

```bash
curl -X POST http://localhost:8081/books/v1/filterBooks \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gatsby",
    "createdAt": "2025-07-09",
    "pageNo": 0,
    "pageSize": 10
  }'
```

## Notes

- Date-only strings (e.g., "2025-07-09") are automatically converted to midnight time (00:00:00)
- The API supports case-insensitive partial matching for book names
- All date comparisons are exact matches (not ranges)
- Pagination is 0-based (first page is 0)
