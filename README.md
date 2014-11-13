MinersMail
==========

Per server in-game mail system driven by pre-existing items and slash commands.

 

Basic functionality should include:

   -ability to mail a single user, regardless of online status

   -alerts regarding new messages since last date of signing into the server as well as immediate updates if a mail is received while logged in

   -ability to retrieve new messages


Introduction:


The idea is to facilitate easier communication between players regardless of online status as well as a way to maintain more permanence of messages while reducing reliance on regular in-game chat.


Project Plan:

Our project consists of an in game mailing system for users. Once the user creates a book they can leave a message for another user in the game. There will be a character limit in the messages to keep the message concise.

Technical Approach:

Commands

/m <recipient name> <message body>

<recipient name> is the person to whom the message should be delivered

<message body> is the message to be sent. has a 80 character limit

/m_get

    attempts to retrieve unclaimed mail for the given user

/m_help

    displays help information regarding available commands

 

Mailbox

A hashtable of user name / MailNode pairs



MailNode

has 4 fields: String messageBody, String sender, MailNode next and TimeStamp timestamp

placeholder MailNodes have all fields set to NULL



On Login

If the current user is not in the Mailbox hashtable

   put the current user / placeholder MailNode association into the Mailbox hashtable

   alert the user a mailbox has been created

Else

   If the MailNode associated with the current user is not a placeholder

      alert the current user that they have unclaimed mail

   Else

      alert the current user that they have no unclaimed mail