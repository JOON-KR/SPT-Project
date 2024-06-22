import { Link } from 'react-router-dom';
import styles from './SearchCss/Navbar.module.css';

const Navbar = () => (
  
  <div className={styles.navbar}>
    <Link to="/monthlySeries"><button>이달의 시리즈</button></Link>
    <Link to="/bestSeries"><button>명예의 전당</button></Link>    
  </div>
);

export default Navbar;
