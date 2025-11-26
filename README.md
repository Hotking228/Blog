# Simple blog
Пет проект с использованием JDBC, Java Core, HTTPServlets, Tomcat, lombok, Maven, JUnit5.
## Описание
Проект представляет собой простой блог, в котором главной целью было - изучение и использование инструментов Maven(зависимости, плагины и тд.), 
JUnit5
Maven используется для Project LifeCycle.
Все инструменты уже подробно мной описаны в моих предыдущих проектах:
https://github.com/Hotking228/JDBCPetProj.git
https://github.com/Hotking228/Smart_team_task_manager.git
Сейчас опишу только новые - JUnit5 и Apache Maven.
## JUnit5
Инструмент позволяющий писать автоматизированные тесты. Позволяет разрабатывать каждый модуль независимо, что в рамках
командной разработки незаменимо - можно протестировать обособленно свой модуль или даже просто метод/функционал.
Существует 3 вида тестов: Unit, Integrity и acceptence. 
Unit тесты это тест отдельно каждого модуля/метода.
Integrity - тест связи модулей и правильной работы их логики
Acceptence - тест всего приложения в целом.
В моем проекте я использовал только Unit и Integrity тесты.
Рассмотрим пример на каждый вид теста:
Unit:
```java
    @ParameterizedTest //параметризованный тест
    @MethodSource("getArgumentsForFindAuthorByUsernameOrEmailAndPassword")//источник данных
    @DisplayName("Поиск авторов по имени пользователя или почте и паролю - для логина")//красивое имя для отображения теста в IDEA
    void findAuthorByUsernameOrEmailAndPasswordTest(String username, String email, String password){
        Optional<AuthorDto> author = AuthorDao.getInstance().findAuthorByUsernameOrEmailAndPassword(username, email, password);
        if((username.equals("dummy") && email.equals("dummy")) || password.equals("dummy")){
            assertThat(author).isEmpty();
        }else{
            assertThat(author).isPresent();
        }
    }

    static Stream<Arguments> getArgumentsForFindAuthorByUsernameOrEmailAndPassword(){// аргументы представляют собой поток данных, создаем его
        return Stream.of(Arguments.of("ivan_petrov", "ivan@example.com", "password123"),
                         Arguments.of("max_code", "dummy", "max_secure"),
                         Arguments.of("dummy", "anna@example.com", "anna_pass"),
                         Arguments.of("dummy", "dummy", "dummy"),
                         Arguments.of("alex_tech", "alex@tech.org", "dummy"),
                         Arguments.of("vano", "dummy", "123")

                );
    }
```
как это выглядит в IDEA:

<img width="546" height="204" alt="image" src="https://github.com/user-attachments/assets/ab68f5ad-fc00-4b0d-9265-acb44b94e3f0" />

как можно увидеть все тесты пройдены.
Integrity тест:
```java
@Test
    void getAllPostsTest(){
        List<PostDto> list = PostsService.getInstance().getAllPosts();
        assertThat(list).hasSizeGreaterThanOrEqualTo(6);
    }
```
Выглядит аналогично unit тесту, но метод getAllPosts обращается к другому модулю проекта(database):
```java
public List<PostDto> getAllPosts(){
        return PostsDao.getInstance().findAll()
                .stream()
                .map(post -> PostDto.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .build())
                .collect(toList());
    }
```
## Maven
Теперь рассмотрим использование Maven в моем проекте:
Для начала была создана родительская pom, в которой в dependencyManagement были указаны зависимости для всех модулей.
Далее каждый модуль при необходимости подтягивал себе все нужные ему зависимости.
```xml
<dependencyManagement>
        <dependencies>
            <!-- JSTL -->
            <dependency>
                <groupId>org.glassfish.web</groupId>
                <artifactId>jakarta.servlet.jsp.jstl</artifactId>
                <version>3.0.1</version>
            </dependency>
            <dependency>
                <groupId>jakarta.servlet.jsp.jstl</groupId>
                <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
                <version>3.0.0</version>
            </dependency>

            <!-- Servlet API -->
            <dependency>
                <groupId>jakarta.servlet</groupId>
                <artifactId>jakarta.servlet-api</artifactId>
                <version>6.0.0</version>
            </dependency>

            <!-- База данных -->
            <dependency>
                <groupId>org.postgresql</groupId>
                <artifactId>postgresql</artifactId>
                <version>42.7.8</version>
            </dependency>

            <!-- Тестирование -->
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-api</artifactId>
                <version>5.11.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>5.11.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-params</artifactId>
                <version>5.11.0</version>
                <scope>test</scope>
            </dependency>

            <!-- Lombok -->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.30</version>
                <scope>provided</scope>
            </dependency>

            <!-- AssertJ и Mockito -->
            <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-core</artifactId>
                <version>3.27.6</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-core</artifactId>
                <version>5.20.0</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.mockito</groupId>
                <artifactId>mockito-junit-jupiter</artifactId>
                <version>5.20.0</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
Так же для удобного поиска статьей был реализован алгоритм поиска редакционного расстояние(по Левенштейну):
```java
public List<PostDto> getPostByTitleLevenshtein(String title){
        List<PostDto> list = getAllPosts();
        PriorityQueue<PostDto> queue = new PriorityQueue<>(Comparator.comparingInt(post -> {
            String titleFromDb = post.getTitle();
            return calculateDistance(title, titleFromDb);
        }));
        List<PostDto> ans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            queue.add(list.get(i));
        }
        while(!queue.isEmpty()){
            ans.add(queue.poll());
        }
        return ans;
    }

    private Integer calculateDistance(String title, String titleFromDb){

        int[][]dp = new int[title.length() + 1][titleFromDb.length() + 1];
        for (int j = 0; j < dp.length; j++) {
            dp[j][0] = j;
        }
        for (int j = 0; j < dp[0].length; j++) {
            dp[0][j] = j;
        }
        for (int j = 1; j < dp.length; j++) {
            for (int k = 1; k < dp[j].length; k++) {
                dp[j][k] = Math.min(Math.min(dp[j - 1][k] + 1,dp[j][k - 1] + 1), dp[j - 1][k - 1] + (title.charAt(j - 1) == titleFromDb.charAt(k - 1) ? 0 : 1));
            }
        }

        return dp[dp.length - 1][dp[0].length - 1];
    }
```

Все просто: в очередь с приоритетами с кастомным компаратором добавляем все статьи.
Потом слой сервлетов забирает у нас все статьи и выводит их пользователю:
Пример ввода:
Статьи в неотсортированном виде:

<img width="281" height="254" alt="image" src="https://github.com/user-attachments/assets/1a0ae3ba-352f-42a2-8c37-48fed7e57e76" />

А теперь с ошибками введем называние статьи, пусть:
"Тстиероавние с JnUit5"
нажимаем Search и получаем:

<img width="311" height="251" alt="image" src="https://github.com/user-attachments/assets/b195d7a0-9aed-4244-af82-142bd4e0a213" />

Как видно, порядок статей изменился, и на первом месте теперь интересующая нас статья.
## Вывод
Таким образом, веб-приложение с использованием Maven+Junit5 делается довольно легко: можно протестировать отдельно каждый модуль вне зависимости от наличия/работоспособности
других. Maven позволяет очень легко создавать все зависимости, сам определяет в каком порядки их строить.
