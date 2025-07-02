# Bulk Book Creation API Usage

## Endpoint

`POST /books/v1/addBookBulk`

## Request Format

The request body must be an **array** of book objects, not a single object.

### Correct Format (Array):

```json
[
  {
    "userId": 1,
    "book": {
      "name": "Book 1",
      "price": 100
    }
  },
  {
    "userId": 2,
    "book": {
      "name": "Book 2",
      "price": 150
    }
  }
]
```

### Incorrect Format (Single Object):

```json
{
  "userId": 1,
  "book": {
    "name": "Book 1",
    "price": 100
  }
}
```

## Response Codes

- **200**: All books added successfully
- **207**: Some books added successfully (partial success)
- **400**: Bad request (invalid format, missing fields, or no valid books)
- **500**: Internal server error

## Validation Rules

1. Each book must have a valid `userId` that exists in the database
2. Each book must have a `name` and `price`
3. Book names must be unique (not already exist in the database)
4. The request body must be an array, not a single object

## Example cURL Request

```bash
curl -X POST http://localhost:8080/books/v1/addBookBulk \
  -H "Content-Type: application/json" \
  -d '[
    {
      "userId": 1,
      "book": {
        "name": "The Great Gatsby",
        "price": 25.99
      }
    },
    {
      "userId": 2,
      "book": {
        "name": "To Kill a Mockingbird",
        "price": 19.99
      }
    }
  ]'
```

## Error Handling

The API now properly handles:

- JSON parsing errors (when single object is sent instead of array)
- Missing required fields
- Non-existent users
- Duplicate book names
- Empty request bodies

All errors are returned with appropriate HTTP status codes and descriptive error messages.
