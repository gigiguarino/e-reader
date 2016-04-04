package eecs285.proj4.gguarino;

/**
 * Created by gabrielleguarino on 11/16/15.
 */


import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.util.LinkedList;
import java.util.ListIterator;
import java.net.URL;


public class prototypeFrame extends JFrame
{
    JFrame window;

    // panels
    private JPanel topPanel;
    private JPanel authorPanel;
    private JPanel titlePanel;
    private JPanel centerPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel bottomPanel;

    // image
    private JLabel imgLabel;

    // searches
    private JButton authorSearch;
    private JTextField authorField;
    private JLabel authorLabel;
    private JButton titleSearch;
    private JTextField titleField;
    private JLabel titleLabel;

    // page navigation buttons
    private JButton nextPage;
    private JButton prevPage;
    private JButton lastPage;
    private JButton firstPage;

    // drop down menu
    private JComboBox dropDown;

    // listener
    private buttonListener listen;

    // strings
    private String title = "Book Browser Prototype";

    // bools
    boolean bookSelected;



    // BOOKS -------------------------------------------------------

    LinkedList <book> bookList;
    ListIterator <book> iter;
    book currentBook;
    int currentPage;

    public class book
    {
        // book class
        private String title;
        private String prefix;
        private int numPages;

        String getTitle()
        {
            return title;
        }

        String getPrefix()
        {
            return prefix;
        }

        int getNumPages()
        {
            return numPages;
        }

        URL getImageURL(int pageNum)
        {
            URL returnURL;
            String s;
            String num = String.valueOf(pageNum);
            s = "/images/" + prefix + num + ".jpg";
            returnURL = getClass().getResource(s);
            return returnURL;
        }

        book(String prefixIn, String titleIn, int numPagesIn)
        {
            prefix = prefixIn;
            title = titleIn;
            numPages = numPagesIn;
        }
    }

    //---------------------------------------------------------------


    void popUpSearchWindow()
    {
        // search not implemented yet
        // so this window pops up to let the user know

        JFrame searchWindow;
        searchWindow = new JFrame("Search Error");
        searchWindow.setVisible(true);
        searchWindow.setLayout(new BorderLayout());
        JLabel error = new JLabel("Search function not implemented yet!");
        searchWindow.setSize(300,50);
        searchWindow.add(error, BorderLayout.NORTH);
    }

    book findBook(String title)
    {
        // finds book with corresponding title

        iter = bookList.listIterator();
        book current;

        while(iter.hasNext())
        {
            current = iter.next();
            if (current.getTitle().equals(title))
            {
                return current;
            }
        }

        return iter.previous();
    }


    // BUTTON LISTENER -----------------------------------------------

    public class buttonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            URL url;
            String num;
            String tag;

            if (event.getSource() == authorSearch ||
                    event.getSource() == titleSearch)
            {
                // display window
                // search not implemented yet
                popUpSearchWindow();
            }

            if (event.getSource() == nextPage)
            {
                // show image of next page of book
                // if on last page, show page 1

                if (bookSelected)
                {
                    if (currentPage != currentBook.getNumPages())
                    {
                        url = currentBook.getImageURL(currentPage + 1);
                        num = String.valueOf(currentPage + 1);
                        tag = currentBook.getPrefix() + num;
                        imgLabel.setIcon(new ImageIcon(url, tag));
                        currentPage++;
                    }

                    else
                    {
                        url = currentBook.getImageURL(1);
                        num = "1";
                        tag = currentBook.getPrefix() + num;
                        imgLabel.setIcon(new ImageIcon(url,tag));
                        currentPage = 1;
                    }
                }
            }


            if (event.getSource() == prevPage)
            {
                // show image of previous page of book
                // if on first page, show last page

                if (bookSelected)
                {
                    if (currentPage != 1)
                    {
                        url = currentBook.getImageURL(currentPage - 1);
                        num = String.valueOf(currentPage - 1);
                        tag = currentBook.getPrefix() + num;
                        imgLabel.setIcon(new ImageIcon(url, tag));
                        currentPage--;
                    }

                    else
                    {
                        url = currentBook.getImageURL(currentBook.getNumPages());
                        num = String.valueOf(currentBook.getNumPages());
                        tag = currentBook.getPrefix() + num;
                        imgLabel.setIcon(new ImageIcon(url, tag));
                        currentPage = currentBook.getNumPages();
                    }
                }
            }

            if (event.getSource() == lastPage)
            {
                // show image of last page of book

                if (bookSelected)
                {
                    url = currentBook.getImageURL(currentBook.getNumPages());
                    num = String.valueOf(currentBook.getNumPages());
                    tag = currentBook.getPrefix() + num;
                    imgLabel.setIcon(new ImageIcon(url, tag));
                    currentPage = currentBook.getNumPages();
                }
            }

            if (event.getSource() == firstPage)
            {
                // show image of first page of book

                if (bookSelected)
                {
                    url = currentBook.getImageURL(1);
                    num = "1";
                    tag = currentBook.getPrefix() + num;
                    imgLabel.setIcon(new ImageIcon(url, tag));
                    currentPage = 1;
                }
            }

            if (event.getSource() == dropDown)
            {
                // clicked a drop down menu item

                if (!dropDown.getSelectedItem().equals(""))
                {
                    // sets the current book to chosen book
                    // displays first page of book

                    currentBook = findBook(dropDown.getSelectedItem().toString());
                    bookSelected = true;
                    url = currentBook.getImageURL(1);
                    num = "1";
                    tag = currentBook.getPrefix() + num;
                    imgLabel.setIcon(new ImageIcon(url, tag));
                    currentPage = 1;
                }
            }

        }
    }

    //------------------------------------------------------------------





    // INITIALIZERS ---------------------------------------------------

    void dropDownInitializer()
    {
        // the drop down menu holds the titles of the books

        dropDown = new JComboBox();
        iter = bookList.listIterator();
        dropDown.addItem("");

        while (iter.hasNext())
        {
            dropDown.addItem(iter.next().getTitle());
        }
    }

    void centerPanelInitializer()
    {
        // left panel is where the left buttons are
        // <, <<
        leftPanel = new JPanel(new GridLayout(2,1));
        leftPanel.add(prevPage);
        leftPanel.add(firstPage);

        // right panel is where the right buttons are
        // >, >>
        rightPanel = new JPanel(new GridLayout(2,1));
        rightPanel.add(nextPage);
        rightPanel.add(lastPage);

        // no book selected
        // center panel is where the book image is shown
        centerPanel = new JPanel(new FlowLayout());
        URL imgURL = getClass().getResource("/images/NoBookSelected.png");
        System.out.println("URL: " + imgURL);
        imgLabel = new JLabel(new ImageIcon(imgURL, "No Book"));
        centerPanel.add(imgLabel);

        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(leftPanel);
        bottomPanel.add(centerPanel);
        bottomPanel.add(rightPanel);
        window.add(bottomPanel, BorderLayout.SOUTH);
    }

    void topPanelInitializer()
    {
        // top panel contains the drop down menu
        // title search and author search
        // stays at top of page

        topPanel = new JPanel(new GridLayout(3,1));
        topPanel.add(dropDown);
        topPanel.add(authorPanel);
        topPanel.add(titlePanel);

        window.add(topPanel, BorderLayout.NORTH);
    }

    void authorInitializer()
    {
        // author search box

        authorPanel = new JPanel(new FlowLayout());

        authorLabel = new JLabel("Author");
        authorField = new JTextField(10);
        authorSearch = new JButton("Search");

        authorPanel.add(authorLabel);
        authorPanel.add(authorField);
        authorPanel.add(authorSearch);
    }

    void titleInitializer()
    {
        // title search box

        titlePanel = new JPanel(new FlowLayout());

        titleLabel = new JLabel("Title");
        titleField = new JTextField(10);
        titleSearch = new JButton("Search");

        titlePanel.add(titleLabel);
        titlePanel.add(titleField);
        titlePanel.add(titleSearch);
    }

    void buttonInitializer()
    {
        // page buttons

        nextPage = new JButton(">");
        prevPage = new JButton("<");
        lastPage = new JButton(">>");
        firstPage = new JButton("<<");
    }

    void actionInitializer()
    {
        listen = new buttonListener();
        nextPage.addActionListener(listen);
        prevPage.addActionListener(listen);
        lastPage.addActionListener(listen);
        firstPage.addActionListener(listen);
        authorSearch.addActionListener(listen);
        titleSearch.addActionListener(listen);
        dropDown.addActionListener(listen);
    }

    // **************************************************

    void bookInitializer()
    {
        // This is the code you would edit if you needed to add more pages
        // or more books

        book book1 = new book("berenstain", "The Berenstain Bears", 4);
        book book2 = new book("oldLady", "There Was an Old Lady Who Swallowed " +
                "a Chick", 4);
        book book3 = new book("pete", "Pete the Cat and His Magic Sunglasses", 4);
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
    }

    // ***************************************************

    //------------------------------------------------------------------






    // CONSTRUCTOR ----------------------------------------

    prototypeFrame()
    {
        // initialize window
        window = new JFrame(title);
        window.setVisible(true);
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bookSelected = false;
        bookList = new LinkedList<book>();

        buttonInitializer();
        authorInitializer();
        titleInitializer();
        bookInitializer();
        dropDownInitializer();
        topPanelInitializer();
        centerPanelInitializer();
        actionInitializer();

        window.pack();
    }
}
