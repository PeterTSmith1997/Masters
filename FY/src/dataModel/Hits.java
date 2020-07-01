package dataModel;

/**
 * @author peter
 * @version 21 Jul 2019
 */
public class Hits {
	
	private String iPaddress;
	private String request;
	private String dateTime;
	private int response;
	private int size;
	private String referer;
	private String userAgent;
	private String protocal;

	/**
	 * @param iPaddress
	 * @param request
	 * @param dateTime
	 * @param response
	 * @param size
	 * @param referer
	 * @param userAgent
	 */
	public Hits(String iPaddress, String request, String protocal, String dateTime,
			int response, int size, String referer, String userAgent) {
		this.iPaddress = iPaddress;
		this.request = request;
		this.protocal = protocal;
		this.dateTime = dateTime;
		this.response = response;
		this.size = size;
		this.referer = referer;
		this.userAgent = userAgent;
	}

	/**
	 * @return the iPaddress
	 */
	public String getiPaddr() {
		return iPaddress;
	}

	/**
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}

	/**
	 * @return the response
	 */
	public int getResponse() {
		return response;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent() {
		return userAgent;
	}

	/**
	 * @return the protocal
	 */
	public String getProtocal() {
		return protocal;
	}

	/**
	 * @param iPaddress the iPaddress to set
	 */
	public void setiPaddr(String iPaddr) {
		this.iPaddress = iPaddr;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(int response) {
		this.response = response;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	/**
	 * @param protocal the protocal to set
	 */
	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result
				+ ((iPaddress == null) ? 0 : iPaddress.hashCode());
		result = prime * result
				+ ((protocal == null) ? 0 : protocal.hashCode());
		result = prime * result + ((referer == null) ? 0 : referer.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result + response;
		result = prime * result + size;
		result = prime * result
				+ ((userAgent == null) ? 0 : userAgent.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Hits other = (Hits) obj;
		if (dateTime == null) {
			if (other.dateTime != null) {
				return false;
			}
		} else if (!dateTime.equals(other.dateTime)) {
			return false;
		}
		if (iPaddress == null) {
			if (other.iPaddress != null) {
				return false;
			}
		} else if (!iPaddress.equals(other.iPaddress)) {
			return false;
		}
		if (protocal == null) {
			if (other.protocal != null) {
				return false;
			}
		} else if (!protocal.equals(other.protocal)) {
			return false;
		}
		if (referer == null) {
			if (other.referer != null) {
				return false;
			}
		} else if (!referer.equals(other.referer)) {
			return false;
		}
		if (request == null) {
			if (other.request != null) {
				return false;
			}
		} else if (!request.equals(other.request)) {
			return false;
		}
		if (response != other.response) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		if (userAgent == null) {
			if (other.userAgent != null) {
				return false;
			}
		} else if (!userAgent.equals(other.userAgent)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IP address=" + iPaddress + ", request=" + request + ", dateTime="
				+ dateTime + ", response=" + response + ", size=" + size
				+ ", referer=" + referer + ", userAgent=" + userAgent
				+ ", protocal=" + protocal;
	}
	
}