package com.xtt.mediatheque.messages;

import com.xtt.mediatheque.exceptions.MessageException;

//@Component
public class MessageUtils {

//	@Autowired
//	private MessageSource messageSource;

	public String getMessageWithParameters(String key, String[] args) throws MessageException {
		String message = "";
//		try {
//			message = messageSource.getMessage(key, args, Locale.FRENCH);
//		} catch (NoSuchMessageException e) {
//			throw new MessageException(e.getMessage());
//		}
		return message;
	}

	public String getMessage(String key) throws MessageException {
		return this.getMessageWithParameters(key, null);
	}

}
