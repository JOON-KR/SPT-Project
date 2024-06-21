import { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { getUserByKeyword } from './RESTapi';

const UserSearchResults = ({ keyword }) => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const userData = await getUserByKeyword(keyword);
        setUsers(userData);
      } catch (error) {
        console.error('유저 검색에 실패하였습니다', error);
      }
    };

    fetchUsers();
  }, [keyword]);

  return (
    <div>
      <h3>유저 검색결과</h3>
      {users && users.length > 0 ? (
        <ul>
          {users.map((user) => (
            <li key={user.id}>{user.name}</li>
          ))}
        </ul>
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
