# Список методов для Course, Chapter и Lesson

## **Course Service**
- **createCourse(CourseDto courseDto)** — Создание нового курса.
- **getCourseById(Long courseId)** — Получение информации о курсе по ID.
- **getAllCourses()** — Получение всех курсов.
- **updateCourse(Long courseId, CourseDto courseDto)** — Обновление данных курса.
- **deleteCourse(Long courseId)** — Удаление курса.
- **addChapterToCourse(Long courseId, ChapterDto chapterDto)** — Добавление главы в курс.
- **removeChapterFromCourse(Long courseId, Long chapterId)** — Удаление главы из курса.

## **Chapter Service**
- **createChapter(ChapterDto chapterDto)** — Создание новой главы.
- **getChapterById(Long chapterId)** — Получение информации о главе по ID.
- **getChaptersByCourseId(Long courseId)** — Получение всех глав для конкретного курса.
- **updateChapter(Long chapterId, ChapterDto chapterDto)** — Обновление данных главы.
- **deleteChapter(Long chapterId)** — Удаление главы.
- **addLessonToChapter(Long chapterId, LessonDto lessonDto)** — Добавление урока в главу.
- **removeLessonFromChapter(Long chapterId, Long lessonId)** — Удаление урока из главы.

## **Lesson Service**
- **createLesson(LessonDto lessonDto)** — Создание нового урока.
- **getLessonById(Long lessonId)** — Получение информации об уроке по ID.
- **getLessonsByChapterId(Long chapterId)** — Получение всех уроков для конкретной главы.
- **updateLesson(Long lessonId, LessonDto lessonDto)** — Обновление данных урока.
- **deleteLesson(Long lessonId)** — Удаление урока.

## **Additional Utility Methods (Optional)**
- **searchCourses(String searchTerm)** — Поиск курсов по ключевым словам.
- **searchChapters(String searchTerm)** — Поиск глав по ключевым словам.
- **searchLessons(String searchTerm)** — Поиск уроков по ключевым словам.
- **sortCoursesByDate()** — Сортировка курсов по дате создания или обновления.
- **sortChaptersByOrder()** — Сортировка глав по порядку (order).
- **sortLessonsByOrder()** — Сортировка уроков по порядку (order).
