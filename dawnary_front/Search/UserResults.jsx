import PropTypes from 'prop-types';
import { useEffect , useState } from 'react';
import { getUserByKeyword } from './RESTapi';
import "./SearchCss/Result.css"

const UserResults = ({ keyword }) => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const usersData = await getUserByKeyword(keyword);
        setUsers(usersData);
      } catch (error) {
        console.error('검색에 실패하였습니다', error);
      }
    };

    fetchUsers();
  }, [keyword]);

  return (
    <div>
      <h3>유저 검색결과</h3>
      {users && users.length > 0 ? (
        <ul>
          {users.map((user, index) => (
            <li key={index}>{user.name}</li>
          ))}
        </ul>
      ) : (
        <p>유저 검색 결과가 없습니다.</p>
      )}
      <hr />
    </div>
  );
};

UserResults.propTypes = {
  keyword: PropTypes.string.isRequired,
};

export default UserResults;
