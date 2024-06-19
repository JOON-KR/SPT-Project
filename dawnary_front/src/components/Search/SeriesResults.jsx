import { useEffect, useState } from "react"
import PropTypes from "prop-types"
import { getSeriesByKeyword } from "../RESTapi"

const SeriesResults = ({ keyword }) => {
  const [series, setSeries] = useState([])

  useEffect(() => {
    const fetchSeries = async () => {
      try {
        const seriesData = await getSeriesByKeyword(keyword)
        setSeries(seriesData)
      } catch (error) {
        console.error('검색에 실패하였습니다', error)
      }
    };

    fetchSeries()
  }, [keyword])

  return (
    <div>
      <h3>시리즈 검색결과</h3>
      <ul>
        {series && Array.isArray(series) && series.map((item, index) => (
          <li key={index}>{item.title}</li>
        ))}
      </ul>
    </div>
  )
}

SeriesResults.propTypes = {
  keyword: PropTypes.string.isRequired
}

export default SeriesResults
