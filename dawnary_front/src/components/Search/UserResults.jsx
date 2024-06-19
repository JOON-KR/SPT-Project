import { useEffect, useState } from "react";
import PropTypes from "prop-types";
import { getUserByKeyword } from "../RESTapi"

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
      <ul>
        {users && Array.isArray(users) && users.map((user, index) => (
          <li key={index}>{user.name}</li>
        ))}
      </ul>
    </div>
  );
};

UserResults.propTypes = {
  keyword: PropTypes.string.isRequired
};

export default UserResults;
