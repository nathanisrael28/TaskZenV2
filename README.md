# TASKZEN V2 – PROGRESS TRACKER

✅ SPRINT 1: CORE SETUP + BASIC CRUD
------------------------------------

- Project setup with v2 package structure
- Entity + DTO created with Builder pattern
- Status Enum (PENDING, COMPLETED)
- TaskController with CRUD + PATCH
- Service layer with Optional, clean mapping
- Manual DTO-Entity mapping
- Validation annotations: @NotBlank, @Future
- Exception Handling basic done

🚧 Remaining in Sprint 1:

- [ ] Convert all service returns to Optional
- [ ] Add default status logic in service (if null, set PENDING)
- [ ] DTO clean builder & mapping refactor (if any pending)
- [ ] Final round testing in Swagger/Postman

✅ SPRINT 2: JAVA 8 ENHANCEMENTS
------------------------------------
⏳ Yet to start:

- Use `Optional` meaningfully in service
- Convert all for-loops → `stream().map()`
- Add a `TaskMapper` utility class
- Implement `TaskFilterStrategy` using functional interface
- Add filtering endpoint (by title/status)
- Use predicates, lambda expressions

✅ SPRINT 3: SWAGGER + VALIDATION + EXCEPTION
------------------------------------
✔ Swagger: @Operation, @ApiResponse, @Parameter done
✔ GlobalExceptionHandler created
✔ Validation working
✔ Swagger UI fixed (version mismatch resolved)

--------------------------------------------

✅ TASKZEN V2 – Sprint 1: Pending Items List markdown
=======================


✅ Sprint 1 PENDING WORK
=======================

1️⃣ Default Status Logic:
--------------------------
🛠 In Service layer (createTask method):

- If status in DTO is null → set it to PENDING before saving.

🔁 Sample logic:

```
if (taskResponseDto.getStatus() == null) {
    task.setStatus(Status.PENDING);
} else {
    task.setStatus(taskResponseDto.getStatus());
}
```

2️⃣ Optional Usage in Service:
🛠 Replace null checks with Optional

In getTask(id), use Optional cleanly

For example:

```
return repo.findById(id)
           .map(TaskMapper::mapToDto)
           .orElseThrow(() -> new TaskNotFoundException(id));
 ```

3️⃣ Manual Mapper Utility (if not done):
📦 Create a TaskMapper class under mapper/ package:

```
public class TaskMapper {

    public static TaskEntity mapToEntity(TaskDto dto) {
        return TaskEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .status(dto.getStatus())
                .build();
    }

    public static TaskDto mapToDto(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }
}
```

Then update your service to use this for conversion.

4️⃣ Final Sanity Testing:
🧪 Use Swagger/Postman to check:

✅ POST /task

✅ GET /tasks

✅ GET /task/{id}

✅ PUT /task

✅ PATCH /task/{id}/status

✅ DELETE /task/{id}

Verify:

Status defaulting works

Validations hit correctly

ResponseEntity status codes are correct

GlobalExceptionHandler triggers properly

🎯 Once all these ✅, then we say:

🏁 Sprint 1 – “MISSION COMPLETED”
________________________________________

Before using builder() in mapToEntity() and mapToDto(), you need to enable the builder pattern in both the Entity and
DTO using Lombok.

✅ Step-by-step Setup for Builder
📦 1. In TaskEntity.java

````
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Status status;
}
````

📦 2. In TaskDto.java

````
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    @NotBlank(message = "Title must not be blank")
    private String title;

    @Size(min = 10, message = "Description must be at least 10 characters")
    private String description;

    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    private Status status;
}
````

✅ After This, You Can Write Mappers Like:

````
public class TaskMapper {

    public static TaskEntity mapToEntity(TaskDto dto) {
        return TaskEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .dueDate(dto.getDueDate())
                .status(Optional.ofNullable(dto.getStatus()).orElse(Status.PENDING))
                .build();
    }

    public static TaskDto mapToDto(TaskEntity entity) {
        return TaskDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .status(entity.getStatus())
                .build();
    }
}
````

❗Reminder:
If @Builder not given → TaskEntity.builder() or TaskDto.builder() won't compile. So you're absolutely right to prepare
the DTO/Entity first 👏

# Without lombok ?

ChatGPT said:
🔥 Super intelligent question ra mama!
Yes, you can absolutely use builder pattern manually without Lombok — but you'll have to write the boilerplate code
yourself for both DTO and Entity.

Let’s do that like old-school warriors without Lombok 💪

✅ Manual Builder Pattern Without Lombok
📦 1. TaskEntity.java (Manual Builder)

````
public class TaskEntity {
private Long id;
private String title;
private String description;
private LocalDate dueDate;
private Status status;

    // ✅ Private constructor
    private TaskEntity(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
    }

    // ✅ Static nested Builder class
    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private Status status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskEntity build() {
            return new TaskEntity(this);
        }
    }

    // ✅ Optional getters/setters (if needed for JPA)
    // public Long getId() {...} 
}
````

📦 2. TaskDto.java (Manual Builder)

```
public class TaskDto {
private Long id;
private String title;
private String description;
private LocalDate dueDate;
private Status status;

    private TaskDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.status = builder.status;
    }

    public static class Builder {
        private Long id;
        private String title;
        private String description;
        private LocalDate dueDate;
        private Status status;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder dueDate(LocalDate dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskDto build() {
            return new TaskDto(this);
        }
    }

    // ✅ Optional getters/setters for validation
}
```

✨ Example usage (in mapper):

````
TaskEntity task = new TaskEntity.Builder()
.title(dto.getTitle())
.description(dto.getDescription())
.dueDate(dto.getDueDate())
.status(Status.PENDING)
.build();
````

✅ Summary:
Feature Lombok Manual
Less boilerplate ✅ ❌ (More code)
Fine-grained control ❌ ✅
Good for learning ⚠️ ✅🔥
Production-ready ✅ ✅

_______________________________
Builder Pattern – Chinna Brief Explanation
🔧 What is it?
Builder Pattern is a creational design pattern that lets you build complex objects step-by-step, instead of using big
constructors with too many parameters.

🧠 Why Should I Use It?
Imagine this constructor:

new TaskEntity(101L, "Task", "Desc", LocalDate.now(), Status.PENDING);
🥴 Chala confusing untadhi, especially when you have multiple String or Date types — order wrong pedithe bugs vasthai.

Now with builder:

new TaskEntity.Builder()
.id(101L)
.title("Task")
.description("Desc")
.dueDate(LocalDate.now())
.status(Status.PENDING)
.build();
➡️ Easy to read
➡️ No parameter order tension
➡️ Optional fields ni skip cheyyachu
➡️ Code maintainable & neat

⏱ When to Use?
Use Case Builder Useful?
Too many constructor parameters ✅ YES
Some fields are optional ✅ YES
Immutable object kavali (no setters)    ✅ YES
Fluent API kavali (method chaining)    ✅ YES
JPA Entity with 2-3 fields max ❌ May not need

🧰 How Does It Work?
Internally, you create a static nested Builder class with the same fields, and a build() method that returns the main
object.

public static class Builder {
private Long id;
private String title;
...
public Builder title(String title) {
this.title = title;
return this;
}
public TaskEntity build() {
return new TaskEntity(this);
}
}
🛡️ Advantages
No need to remember constructor parameter order

More readable & maintainable

Null-safe if you want defaults

Helps when number of fields increases

❗ Downsides
Bit of boilerplate code (unless using Lombok)

Slightly verbose for very simple classes

🎓 In Interviews?
If they ask:

“Why builder pattern?”

You say:

"To simplify object creation when constructors get too messy, especially when we have optional and mandatory fields.
It's also useful to make our code readable and safe from parameter misplacement."

✅ That’s the scoop mama!

Ready to go full builder style in TaskZen?
Let’s fire up the next step if you’re ready 🧱🔥