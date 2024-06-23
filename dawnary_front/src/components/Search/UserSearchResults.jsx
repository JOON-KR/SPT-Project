import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { SearchMemberByKeyword } from './RESTapi';
import styles from './SearchCss/UserSearchResults.module.css';

const UserSearchResults = ({ keyword }) => {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  const itemsPerPage = 5;
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentUsers = users.slice(indexOfFirstItem, indexOfLastItem);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const userData = await SearchMemberByKeyword(keyword);
        setUsers(userData);
      } catch (error) {
        console.error('유저 검색에 실패하였습니다', error);
      }
    };

    fetchUsers();
  }, [keyword]);

  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className={styles.userSearchContainer}>
      <h3>유저 검색결과</h3>
      {currentUsers.length > 0 ? (
        <>
          <ul className={styles.userList}>
            {currentUsers.map((user) => (
              <li key={user.id} className={styles.userItem}>
                <img src={user.imagePath} alt="User" className={styles.userImage} />
                <div className={styles.userInfo}>
                  <p className={styles.userName}>{user.name}</p>
                  <p className={styles.userEmail}>{user.email}</p>
                </div>
              </li>
            ))}
          </ul>
          <div>
            {users.length > itemsPerPage && (
              <ul className={styles.pagination}>
                {Array(Math.ceil(users.length / itemsPerPage))
                  .fill()
                  .map((_, index) => (
                    <li key={index} onClick={() => paginate(index + 1)}>
                      {index + 1}
                    </li>
                  ))}
              </ul>
            )}
          </div>
        </>
      ) : (
        <p>유저 검색 결과가 없습니다.</p>
      )}
    </div>
  );
};

UserSearchResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default UserSearchResults;
