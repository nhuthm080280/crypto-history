#How to start app at local env
run ./gradlew clean build

run docker-compose up --build --force-recreate

#Deploy to heroku
1. https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku
2. Download heroku cli
3. Execute cmd "heroku login"![img_1.png](img_1.png)
4. Go to project folder. Example: cd crypto-history![img_2.png](img_2.png)
5. Execute cmd "heroku create"![img.png](img.png)
6. Execute cmd "git push heroku master"