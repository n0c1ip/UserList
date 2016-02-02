package DAO;

import objects.User;

import java.util.List;

public class UserService {

        private static UserDao userDao;

        public UserService() {
            userDao = new UserDao();
        }

        public void persist(User entity) {
            userDao.openCurrentSessionwithTransaction();
            userDao.persist(entity);
            userDao.closeCurrentSessionwithTransaction();
        }

        public void update(User entity) {
            userDao.openCurrentSessionwithTransaction();
            userDao.update(entity);
            userDao.closeCurrentSessionwithTransaction();
        }

    //TODO findByid & Find/DeleteAll
/*
        public User findById(String id) {
            userDao.openCurrentSession();
            User user = userDao.findById(id);
            userDao.closeCurrentSession();
            return user;
        }


        public void delete(String id) {
            userDao.openCurrentSessionwithTransaction();
            User book = userDao.findById(id);
            userDao.delete(book);
            userDao.closeCurrentSessionwithTransaction();
        }



        public List<User> findAll() {
            userDao.openCurrentSession();
            List<User> books = userDao.findAll();
            userDao.closeCurrentSession();
            return books;
        }

        public void deleteAll() {
            userDao.openCurrentSessionwithTransaction();
            userDao.deleteAll();
            userDao.closeCurrentSessionwithTransaction();
        }

        public UserDao userDao() {
            return userDao;
        }
    }
*/

}
