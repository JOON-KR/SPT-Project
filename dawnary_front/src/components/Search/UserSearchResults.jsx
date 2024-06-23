import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { SearchMemberByKeyword } from './RESTapi';
import styles from './SearchCss/UserSearchResults.module.css';
import _ from 'lodash';

const UserSearchResults = ({ keyword }) => {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);

  const itemsPerPage = 5;

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

  const filteredUsers = users.filter(user =>
    _.includes(user.name, keyword)
  );

  

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentUsers = filteredUsers.slice(indexOfFirstItem, indexOfLastItem); // 페이지네이션된 필터된 유저

  const totalPages = Math.ceil(filteredUsers.length / itemsPerPage);

  // 데이터 확인용
  // console.log(filteredUsers)
  // console.log(currentUsers)

  return (
    <div className={styles.userSearchContainer}>
      <h3>유저 검색결과</h3>
      {currentUsers.length > 0 ? (
        <>
          <ul className={styles.userList}>
            {currentUsers.map((user) => ( // 현재 페이지의 유저만 렌더링
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
            {totalPages > 1 && (
              <ul className={styles.pagination}>
                <li onClick={() => paginate(1)} className={currentPage === 1 ? styles.disabled : ''}>
                  처음
                </li>
                {Array(totalPages)
                  .fill()
                  .map((_, index) => (
                    <li key={index} onClick={() => paginate(index + 1)} 
                        className={currentPage === index + 1 ? styles.active : ''}>
                      {index + 1}
                    </li>
                  ))}
                <li onClick={() => paginate(totalPages)} className={currentPage === totalPages ? styles.disabled : ''}>
                  마지막
                </li>
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
