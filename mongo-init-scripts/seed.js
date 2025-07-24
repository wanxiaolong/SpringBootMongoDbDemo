// 连接到 'movie_db' 数据库 (如果不存在则会自动创建)
db = db.getSiblingDB('movie_db');

// 创建一个集合，这样数据库才会在listDatabases中显示
db.createCollection("movies");

// (可选，但推荐隔离应用权限)：为应用程序创建读写用户
db.createUser(
  {
    user: "app_user",
    pwd: "app_password", // 实际应用连接的密码
    roles: [
      { role: "readWrite", db: "movie_db" }
    ]
  }
);

// 可选：插入一些初始数据
db.movies.insertOne({
  year: 2024,
  title: "Initial Movie from Script",
  director: "Init Script",
  genre: "Test",
  likes: 0
});