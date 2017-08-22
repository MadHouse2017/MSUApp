package com.madhouse.msu.v10.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Text;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.madhouse.msu.v10.MSU.LoginScreen;
import com.madhouse.msu.v10.MSU.RegistrationDetails;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.SpinnerAdapterHelpdesk;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Krishna on 8/12/2017.
 */
public class GovernmentFunding extends Fragment{

    private TextInputLayout  inputLayoutOldPassword,inputLayoutNewPassword,inputLayoutReNewPassword;
    private EditText inputOldPassword,inputNewPassword,inputReNewPassword;
    private Button changePassSubmit;
    private static UserPreferences pref;
    private ProgressDialog pd;
    private ChangePasswordProxie changePassProxie;
    private ChangePassBean changePassBean;
    private TextView lName, lId, lEmail, lMob, lbussType;
    private static EditText groupName;
    private static EditText noOfMembers;
    private static EditText businessDesc;
    private Spinner required_fund;
    private static String fundingName;
    private String fundingID;
    private Button createPDFBtn;
    private File file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_govfunding, container, false);
        pref = UserPreferences.getInstance(getActivity());
        pd = new ProgressDialog(getActivity());



        lName = (TextView)  rootView.findViewById(R.id.name);
        lId = (TextView)  rootView.findViewById(R.id.id_number);
        lEmail = (TextView)  rootView.findViewById(R.id.emailid);
        lMob = (TextView)  rootView.findViewById(R.id.mobileno);
        lbussType = (TextView)  rootView.findViewById(R.id.business_type);

        lName.setText("Leader Name | "+pref.getNAME());
        lId.setText("Approved ID | "+pref.getGOV_ID());
        lEmail.setText("Email ID | "+pref.getEmail());
        lMob.setText("Mobile No. | "+pref.getPRIMARY_MOB());
        lbussType.setText("Business Type | "+pref.getPREFERENCE_HOBBY());


        groupName = (EditText) rootView.findViewById(R.id.groupname);
        businessDesc = (EditText) rootView.findViewById(R.id.business_desc);
        noOfMembers = (EditText) rootView.findViewById(R.id.no_of_memebrs);

        required_fund = (Spinner) rootView.findViewById(R.id.funding_needed);

        createPDFBtn = (Button) rootView.findViewById(R.id.proceesButton);
        required_fund.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner,  getResources().getStringArray(R.array.funding_needed),
                getResources().getStringArray(R.array.funding_needed_val)));

        required_fund.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                TextView divId = (TextView) view.findViewById(R.id.sub_text_seen);
                TextView divName= (TextView) view.findViewById(R.id.text_main_seen);

                fundingName = String.valueOf(divName.getText().toString());
                fundingID = String.valueOf(divId.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        createPDFBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkValidationonUI()){

                    pd.setMessage("Loading...");
                    pd.setCanceledOnTouchOutside(false);
                    pd.show();

                    createFile();

                }
                else {
                        ApplicationUtil.showToast("Please Check you fill all field", getActivity());
                }

            }
        });



        return rootView;
    }



    private boolean checkValidationonUI(){
        String msg = null;
        boolean check=false;

        if (required_fund.getSelectedItemPosition() == 0){
            msg = getString(R.string.text_select_fund);
            required_fund.requestFocus();
            ApplicationUtil.showToast(msg, getActivity());
            check=false;
        }

        else if (TextUtils.isEmpty(groupName.getText())) {
            msg = getString(R.string.fill_details);
            groupName.setError(msg);
            groupName.requestFocus();
            check=false;
        }

        else if (TextUtils.isEmpty(businessDesc.getText())){
            msg = getString(R.string.fill_details);
            businessDesc.requestFocus();
            businessDesc.setError(msg);
            check=false;
        }
        else if (TextUtils.isEmpty(noOfMembers.getText()) || Integer.parseInt(noOfMembers.getText().toString()) > 20){
            msg = getString(R.string.fill_details);
            noOfMembers.requestFocus();
            noOfMembers.setError(msg);
            check=false;
        }


        else{

            check = true;
        }
        return check;
    }


    private void createFile(){

        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File(sdCard.getAbsolutePath() + "/My MSU App");
            directory.mkdirs();

            String fileNameIs = "";

            if (groupName.getText().toString() != null) {
                fileNameIs = groupName.getText().toString() + ".pdf";
            } else {
                fileNameIs = "MSUProposal_" + Math.random() * 1000 + ".pdf";
            }

             file = new File(directory, fileNameIs);
            System.out.println("...directory...." + directory.toString());

            //For Internal Storage
            FileOutputStream fileout = new FileOutputStream(file);


            //create PDF here
            if(createPDF(fileout)){

                System.out.println("......"+sdCard.getAbsolutePath() + "/My MSU App/"+fileNameIs);


                // final Output
                final String imageString = fileToBase64(sdCard.getAbsolutePath() + "/My MSU App/"+fileNameIs);
                //System.out.println("..........."+imageString);

                pd.dismiss();

                callSuccessDialog();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }


        }

        catch (Exception er){
            er.printStackTrace();
        }


    }

    private void callSuccessDialog(){


        final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setCancelable(false);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.promt_message_success);

        Button closeDialog = (Button) dialog.findViewById(R.id.closePrompt);
        closeDialog.setText("Send By Mail");
        TextView messageTitle = (TextView) dialog.findViewById(R.id.alert_title);
        ImageView msg_logo = (ImageView) dialog.findViewById(R.id.msg_logo);
        msg_logo.setVisibility(View.GONE);

        TextView messageDate = (TextView) dialog.findViewById(R.id.alert_date);
        TextView messageDescription = (TextView) dialog.findViewById(R.id.message_description);
        TextView usernameshow = (TextView) dialog.findViewById(R.id.username_show);
        usernameshow.setVisibility(View.GONE);
        TextView passwordshow = (TextView) dialog.findViewById(R.id.password_show);

        messageTitle.setText(getString(R.string.share_title));
        messageDate.setText("");
        messageDescription.setText(getString(R.string.share_pdf));
        passwordshow.setText("Mail To : "+"bhamashah@rajasthan.gov.in");

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Uri path = Uri.fromFile(file);
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
               // set the type to 'email'
                emailIntent .setType("vnd.android.cursor.dir/email");
                String to[] = {"bhamashah@rajasthan.gov.in"};
                emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
               // the attachment
                emailIntent .putExtra(Intent.EXTRA_STREAM, path);
                 // the mail subject
                emailIntent .putExtra(Intent.EXTRA_SUBJECT, "Business Proposal");
                startActivity(Intent.createChooser(emailIntent , "Send email..."));

            }
        });
        // messageDescription.setText(message.getGenre().toString());
        //   messageTitle.setText(message.getTitle().toString());
        dialog.show();


    }




    public boolean createPDF(FileOutputStream fOut){
        System.out.println("File Name : .."+fOut.toString() );
        boolean value= false;
        Document document = new Document(PageSize.A4, 20, 20, 40, 20);
        try {

            PdfWriter writer= PdfWriter.getInstance(document, fOut);

            //Header PDF
            TableHeader event = new TableHeader();
            writer.setPageEvent(event);

            //Footer PDF
            PdfPTable table = new PdfPTable(2);
            table.setTotalWidth(555);
            table.addCell(getCellWithTopBorder("@2017 MSU", Element.ALIGN_LEFT));
            table.addCell(getCellWithTopBorder("Mahila Samuh Udyog by Rajasthan Government",Element.ALIGN_RIGHT));

            FooterTable eventFtr = new FooterTable(table);
            writer.setPageEvent(eventFtr);

            document.open();
            addMetaData(document);
            System.out.println("out of meta page");
            addTitlePage(document);
            System.out.println("out of title page");
           // addContent(document);
           // System.out.println("out of content page");

            System.out.println("....page size...."+document.getPageSize());
            document.close();
            value= true;

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("...error......"+e.toString());
            value= false;
        }finally{
            if(document.isOpen())
            {
                document.close();
            }
        }
        return value;
    }







    class TableHeader extends PdfPageEventHelper {

        String header;
        PdfTemplate total;

        public void setHeader(String header) {
            this.header = header;
        }

        public void onOpenDocument(PdfWriter writer, Document document) {
            total = writer.getDirectContent().createTemplate(30, 16);
        }

        public void onEndPage(PdfWriter writer, Document document) {

            PdfPTable table = new PdfPTable(2);
            try {
                table.setWidths(new int[]{25, 25});
                table.setTotalWidth(555);
                table.setLockedWidth(true);
                table.getDefaultCell().setFixedHeight(25);
                table.getDefaultCell().setBorder(Rectangle.BOTTOM);
                table.getDefaultCell().setBorderWidth(2f);
                table.getDefaultCell().setBorderColor(new BaseColor(232, 111, 20));
                try{
                    Bitmap bmp= BitmapFactory.decodeResource(getActivity().getResources(),
                            R.mipmap.ic_launcher );

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Image img = Image.getInstance(byteArray);
                    img.scaleToFit(200f, 24f);
                    table.addCell(img);

                    //Img 2
                    Bitmap bmp2= BitmapFactory.decodeResource(getActivity().getResources(),
                            R.drawable.appname_hindi_icon);

                    ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                    bmp2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                    byte[] byteArray2 = stream2.toByteArray();

                    Image img2 = Image.getInstance(byteArray2);
                    img2.scaleToFit(200f, 24f);
                    table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table.addCell(img2); //String.format("Page %d of", writer.getPageNumber())
                    /*PdfPCell cell = new PdfPCell(Image.getInstance(total));
                    cell.setBorder(Rectangle.BOTTOM);
                    table.addCell(cell);*/
                    table.writeSelectedRows(0, -1, 20, 836, writer.getDirectContent());
                }
                catch(Exception e){
                    System.out.println("..Inside header in side catch ....."+e.toString());
                }

            }
            catch(DocumentException de) {
                System.out.println("..Inside header ....."+de.toString());
                throw new ExceptionConverter(de);
            }

        }

        public void onCloseDocument(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                    new Phrase(String.valueOf(writer.getPageNumber() - 1)),
                    2, 2, 0);
        }
    }




    public static PdfPCell getCellWithBorderFrHeading(String text, int alignment) {

        Font bf12 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPadding(2f);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.RIGHT|PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
        cell.setBackgroundColor(new BaseColor(163, 162, 161));//#565353
        cell.setBorderWidth(.5f);
        return cell;
    }

    public static PdfPCell getCellWithTopBorder(String text, int alignment) {

        Font bf12 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.TOP);
        cell.setBorderColor(new BaseColor(232, 111, 20));//#565353
        cell.setBorderWidth(2f);
        return cell;
    }




    public class FooterTable extends PdfPageEventHelper {
        protected PdfPTable footer;
        public FooterTable(PdfPTable footer) {
            this.footer = footer;
        }
        public void onEndPage(PdfWriter writer, Document document) {
            footer.writeSelectedRows(0, -1, 20, 19, writer.getDirectContent());
            //footer.addCell(writer.getDirectContent());
        }
    }



    private static void addMetaData(Document document) {
        document.addTitle("Business Platform For Women Using Android App");
        document.addSubject("Developed Mahila Samuh Udyog");
        document.addKeywords("Women Empowerment, Business platform for women, start small business, Training and setup, Participants Details, Training Delivery, Geo Tagging");
        document.addAuthor("Rajsthan Government");
        document.addCreator("MadHouse");
    }




    private static void addTitlePage(Document document)  throws DocumentException {

//        TrainingCntrDetailsBean tcdBean = ApplicationUtil.getInstance().getTcdBean();
//        AuditorInfoBean audiInfo = ApplicationUtil.getInstance().getAdBean();
//        GeoTagingBean geoB = ApplicationUtil.getInstance().getGtBean();

            //System.out.println(".....tcdname........."+tcdBean.getCntrName());

            //Add New
            PdfPTable table_Head = new PdfPTable(1);
            table_Head.setWidthPercentage(100);
            table_Head.addCell(getCellWithBorder("Business Proposal By "+groupName.getText().toString(),PdfPCell.ALIGN_CENTER));
            //table_Head.addCell(getCellWithBorderNoBck(tcdBean.getAddressName()+" ,",PdfPCell.ALIGN_CENTER));
            document.add(table_Head);
            System.out.println("....height...."+table_Head.getTotalHeight()+"...width..."+table_Head.getTotalWidth());


          PdfPTable tableSub = new PdfPTable(1);
        tableSub.setWidthPercentage(100);

        tableSub.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));

        tableSub.addCell(getCellBold("Subject : "+"Request for funding of a business", PdfPCell.ALIGN_LEFT));

        tableSub.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        tableSub.addCell(getCell("", PdfPCell.ALIGN_LEFT));


        tableSub.addCell(getCell("We are a group of women with similar interest collaborating to start a business.", PdfPCell.ALIGN_LEFT));
        tableSub.addCell(getCell("We were brought together and helped by the MSU application. We need funding from the goverment", PdfPCell.ALIGN_LEFT));
        tableSub.addCell(getCell("Here is the necessary information about our business model.", PdfPCell.ALIGN_LEFT));


            float[] columnWidths = {2.25f, 2.75f, 1.00f, 1.00f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);




        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));


        table.addCell(getCellBold("Group Name ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+groupName.getText().toString(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("Business Type ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(": "+pref.getPREFERENCE_HOBBY(), PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

            table.addCell(getCellBold("Number of Members ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+noOfMembers.getText().toString(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

            table.addCell(getCellBold("Business Description ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+businessDesc.getText().toString(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

            table.addCell(getCellBold("Funding/Capital Required	 ", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+fundingName, PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));



        table.addCell(getCellBold("Group Head Details - ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));


            table.addCell(getCellBold("Name	", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+pref.getNAME(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

            table.addCell(getCellBold("Approved ID", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell(": "+pref.getGOV_ID(), PdfPCell.ALIGN_LEFT));
            table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("", PdfPCell.ALIGN_LEFT));





                table.addCell(getCellBold("Email ID ", PdfPCell.ALIGN_LEFT));
                table.addCell(getCell(": "+pref.getEmail(), PdfPCell.ALIGN_LEFT));
                table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
                table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

                table.addCell(getCellBold("Mobile Number", PdfPCell.ALIGN_LEFT));
                table.addCell(getCell(": "+pref.getPRIMARY_MOB(), PdfPCell.ALIGN_LEFT));
                table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
                table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));


        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));



        Calendar c2 = Calendar.getInstance();
        System.out.println("Current time => " + c2.getTime());

        SimpleDateFormat df2 = new SimpleDateFormat("dd MMM yyyy hh:mm:ss"); //new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate2 = df2.format(c2.getTime());

        table.addCell(getCellBold("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_LEFT));
        table.addCell(getCellBold("Date/Time ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(": "+formattedDate2.toString(), PdfPCell.ALIGN_LEFT));


        table.addCell(getCellBold(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell("", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

                table.addCell(getCellBold(" ", PdfPCell.ALIGN_LEFT));
                table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
                table.addCell(getCellBold("Signature", PdfPCell.ALIGN_RIGHT));
                table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));

        table.addCell(getCellBold(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));
        table.addCell(getCell(" ", PdfPCell.ALIGN_RIGHT));





            document.add(tableSub);
            document.add(table);



            System.out.println("....height...."+table.getTotalHeight()+"...width..."+table.getTotalWidth());

            System.out.println("created new page..........");
            // Start a new page
            document.newPage();

    }


    public static PdfPCell getCellWithBorder(String text, int alignment) {

        Font bf12 = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.RIGHT|PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
        cell.setBackgroundColor(new BaseColor(232, 111, 20));//#565353
        cell.setBorderWidth(.5f);
        return cell;
    }


    public static PdfPCell getCellBold(String text, int alignment) {
        Font bf12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setHorizontalAlignment(alignment);
        cell.setPaddingTop(10f);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }


    public static PdfPCell getCell(String text, int alignment) {
        Font bf12 = new Font(Font.FontFamily.HELVETICA, 12);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPaddingTop(10f);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }


    public static PdfPCell createImageCell(Image path) throws DocumentException, IOException {
        Image img = path;
        img.scaleToFit(300f, 150f);
        PdfPCell cell = new PdfPCell(img, false);
        cell.setBorder(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorderColor(BaseColor.BLACK);
        return cell;
    }



//    private static void addContent(Document document) throws DocumentException {
//
//
//
//		  /*System.out.println(".......content..."+tcdBean.getAddressName());
//		  System.out.println("............conten...."+adBean.getCntrType());
//		  System.out.println("....content....."+basicBean.getBiometricAtnd());
//		  System.out.println("....conetnt...."+pBean.getAttndanceReg());
//		  System.out.println("..cinent.."+tdBean.getTrainingDuration());
//		  System.out.println(".......ad..."+audiBean.getAuditorName());*/
//        //System.out.println("......gt....."+geoBean.getAddress());
//        //System.out.println(clAndLab.get(0).getDisplayName());
//        //System.out.println(trainerBean.get(0).getTrainerName());
//
//        if(tcdBean!=null){
//
//            //Table Header
//            PdfPTable table_Head = new PdfPTable(1);
//            table_Head.setWidthPercentage(100);
//            table_Head.addCell(getCellWithBorderFrHeading("Training Centre And Auditor Details",PdfPCell.ALIGN_CENTER));
//            document.add(table_Head);
//
//            float[] columnWidths = {.50f, 2.50f, 1.0f};
//            PdfPTable table_Data = new PdfPTable(columnWidths);
//            table_Data.setWidthPercentage(100);
//
//            table_Data.addCell(getCellWithBorderNoBck("1", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Provider Name", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getTrainingProviderName(), PdfPCell.ALIGN_CENTER));
//
//            table_Data.addCell(getCellWithBorderNoBck("2", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Name", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrName(), PdfPCell.ALIGN_CENTER));
//
//            table_Data.addCell(getCellWithBorderNoBck("3", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre SDMS ID", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getSdmsCntrId(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("4", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Contact No.", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrContactMbNo(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("5", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Email Id", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrEmailId(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("6", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Manager Name", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrMngrName(), PdfPCell.ALIGN_CENTER));
//
//            table_Data.addCell(getCellWithBorderNoBck("7", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Manager Mobile No.", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrMngrMbNo(), PdfPCell.ALIGN_CENTER));
//
//
//            if(tcdBean.getCntrLocatedIn().equalsIgnoreCase("Yes")){
//
//                table_Data.addCell(getCellWithBorderNoBck("8", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Class A City Name", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrLocatedIn(), PdfPCell.ALIGN_CENTER));
//
//            }
//
//            table_Data.addCell(getCellWithBorderNoBck("9", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Training Centre Present on Which floor", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getTrainingCntrFloorNo(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("10", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Project / Scheme", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getProjectScheme(), PdfPCell.ALIGN_CENTER));
//
//            System.out.println("10 records");
//
//            table_Data.addCell(getCellWithBorderNoBck("11", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Job Roles", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getJobRoles(), PdfPCell.ALIGN_CENTER));
//
//            table_Data.addCell(getCellWithBorderNoBck("12", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("State", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getStateName(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("13", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("District", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getDistrictName(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("14", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Constituency", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getConstituencyName(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("15", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Address", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getAddressName(), PdfPCell.ALIGN_CENTER));
//
//            table_Data.addCell(getCellWithBorderNoBck("16", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Pincode", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getPincodeNo(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("17", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("Centre Distance from Road (In KM)", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getCntrDistanceFrmRoadInKM(), PdfPCell.ALIGN_CENTER));
//
//
//            table_Data.addCell(getCellWithBorderNoBck("18", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell(getCellWithBorderNormal("OJT Facility", PdfPCell.ALIGN_LEFT));
//            table_Data.addCell(getCellWithBorderNormal(tcdBean.getOjtFacility(), PdfPCell.ALIGN_CENTER));
//
//            if(adBean!=null){
//
//                table_Data.addCell(getCellWithBorderNoBck("19", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Centre Ownership", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getCntrType(), PdfPCell.ALIGN_CENTER));
//
//
//                if(adBean.getCntrType().equalsIgnoreCase("Franchise Centre"))
//                {
//
//                    table_Data.addCell(getCellWithBorderNoBck("20", PdfPCell.ALIGN_CENTER));
//                    table_Data.addCell(getCellWithBorderNormal("Franchise Name", PdfPCell.ALIGN_LEFT));
//                    table_Data.addCell(getCellWithBorderNormal(adBean.getFranchiseName(), PdfPCell.ALIGN_CENTER));
//
//                }
//
//                table_Data.addCell(getCellWithBorderNoBck("21", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Type of Centre", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOfCntr(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("22", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Mobility of Centre", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOfCntrMorF(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("23", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("PMKVY", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOftrainingConductedOnCntrSF(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("24", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("ESDM", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOftrainingConductedOnCntrNSDC(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("25", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("MoRD", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOftrainingConductedOnCntrMord(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("26", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("State Gov. Scheme", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOftrainingConductedOnCntrStateGov(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("27", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Central Gov. Scheme", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTypOftrainingConductedOnCntrOther(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("28", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Total Area (In SqFt)", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getTotalAreaOfCntr()+" SqFt.", PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("29", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Usable Area for Centre (In SqFt)", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getCntrCarpetArea()+" SqFt.", PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("30", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("No of Classroom", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getNoOfClassRooms(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("31", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("No of Floors", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getNoOfFloors(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("32", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Computer for Administration", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getComputerForAdmin(), PdfPCell.ALIGN_CENTER));
//
//                table_Data.addCell(getCellWithBorderNoBck("33", PdfPCell.ALIGN_CENTER));
//                table_Data.addCell(getCellWithBorderNormal("Furniture Availability ", PdfPCell.ALIGN_LEFT));
//                table_Data.addCell(getCellWithBorderNormal(adBean.getStrFurniture(), PdfPCell.ALIGN_CENTER));
//
//                document.add(table_Data);
//
//                document.newPage();
//
//                System.out.println("added upto....tcd details....."+adBean.getTypOfCntr());
//
//            }
//
//            Paragraph paraSpace = new Paragraph("\r\n"+"\r\n");
//            document.add(paraSpace);
//
//            String typeOfCentreIs = adBean.getTypOfCntr().toString().trim();
//            System.out.println("...Ty of Cntr ...."+typeOfCentreIs);
//            try {
//
//                PdfPTable tableImg = new PdfPTable(2);
//                tableImg.setWidthPercentage(100);
//                PdfPCell c1 = new PdfPCell(new Phrase("Type of Centre : "+typeOfCentreIs));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setBackgroundColor(new BaseColor(137, 138, 139));
//                tableImg.addCell(c1);
//
//                c1 = new PdfPCell(getCell("", Element.ALIGN_JUSTIFIED));  // Add No Border
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                tableImg.addCell(getCell("", Element.ALIGN_JUSTIFIED));
//                tableImg.setHeaderRows(1);
//
//                tableImg.addCell(createImageCell(adBean.getTypeOfCentreImg()));
//				/*if(typeOfCentreIs.equalsIgnoreCase("Rent")){*/
//                document.add(tableImg);
//				/*}*/
//
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//
//            try {
//
//                PdfPTable tableImg = new PdfPTable(2);
//                tableImg.setWidthPercentage(100);
//                PdfPCell c1 = new PdfPCell(new Phrase("Centre ID Proof"));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setBackgroundColor(new BaseColor(137, 138, 139));
//                tableImg.addCell(c1);
//
//                c1 = new PdfPCell(new Phrase("OJT Agreement"));
//                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//                c1.setBackgroundColor(new BaseColor(137, 138, 139));
//                tableImg.addCell(c1);
//                tableImg.setHeaderRows(1);
//
//                tableImg.addCell(createImageCell(tcdBean.getIdProofImg()));
//
//                if(tcdBean.getOjtFacility().equalsIgnoreCase("yes")){
//                    try {
//
//                        tableImg.addCell(createImageCell(tcdBean.getOjtFacilityImg()));
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//                document.add(tableImg);
//
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//
//        }
//
//        System.out.println("ended upto....tcd details");
//
//        Paragraph paraSpaceB = new Paragraph("\r\n"+"\r\n");
//        document.add(paraSpaceB);
//
//        if(basicBean!= null){
//
//            PdfPTable table_Head = new PdfPTable(1);
//            table_Head.setWidthPercentage(100);
//            table_Head.addCell(getCellWithBorderFrHeading("Basic Requirements Details ",PdfPCell.ALIGN_CENTER));
//
//            document.add(table_Head);
//
//            System.out.println("ended upto....bsaic details");
//            createTableBReq(document, basicBean);
//        }
//
//        Paragraph paraSpaceCR = new Paragraph("\r\n"+"\r\n");
//        document.add(paraSpaceCR);
//
//        //document.newPage();
//
//        for(int i=0; i< clAndLab.size(); i++)
//        {
//            PdfPTable table_Head = new PdfPTable(1);
//            table_Head.setWidthPercentage(100);
//            table_Head.addCell(getCellWithBorderFrHeading("Class room  "+(i+1),PdfPCell.ALIGN_CENTER));
//            document.add(table_Head);
//
//            createTableCLI(document, clAndLab.get(i));
//
//        }
//
//        Paragraph paraSpaceLb = new Paragraph("\r\n"+"\r\n");
//        document.add(paraSpaceLb);
//
//
//        // Now add all this to the document
//
//    }



    public static PdfPCell getCellWithBorderNoBck(String text, int alignment) {

        Font bf12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.RIGHT|PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
        //cell.setBackgroundColor(new BaseColor(232, 111, 20));//#565353
        cell.setBorderWidth(.3f);
        return cell;
    }

    public static PdfPCell getCellWithBorderNormal(String text, int alignment) {

        Font bf12 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        PdfPCell cell = new PdfPCell(new Phrase(text,bf12));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.RIGHT|PdfPCell.TOP|PdfPCell.BOTTOM|PdfPCell.LEFT);
        //cell.setBackgroundColor(new BaseColor(232, 111, 20));//#565353
        cell.setBorderWidth(.3f);
        return cell;
    }



//    private static void createTableBReq(Document subCatPart, BasicReqBean brObj)
//            throws BadElementException {
//
//
//        float[] columnWidths = {.50f, 2.50f, 1.0f};
//        PdfPTable table_Data = new PdfPTable(columnWidths);
//        table_Data.setWidthPercentage(100);
//
//        table_Data.addCell(getCellWithBorderNoBck("1", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell(getCellWithBorderNormal("Broadband and Internet Connection", PdfPCell.ALIGN_LEFT));
//        table_Data.addCell(getCellWithBorderNormal(brObj.getBroadbandAndInternetConnection(), PdfPCell.ALIGN_CENTER));
//
//        table_Data.addCell(getCellWithBorderNoBck("2", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Drinking Water");
//        table_Data.addCell(brObj.getDrinkingWater());
//
//        table_Data.addCell(getCellWithBorderNoBck("3", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("No of Washroom");
//        table_Data.addCell(brObj.getWashRoom());
//
//        table_Data.addCell(getCellWithBorderNoBck("4", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("First Aid Kit");
//        table_Data.addCell(brObj.getFirstAidKit());
//
//        table_Data.addCell(getCellWithBorderNoBck("5", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Fire Extenguisher");
//        table_Data.addCell(brObj.getFireExtn());
//
//        table_Data.addCell(getCellWithBorderNoBck("6", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("CCTV Camera");
//        table_Data.addCell(brObj.getCctvCamera());
//
//        table_Data.addCell(getCellWithBorderNoBck("7", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Scheme / Branding");
//        table_Data.addCell(brObj.getSchemeBrandName());
//
//        table_Data.addCell(getCellWithBorderNoBck("8", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Printer");
//        table_Data.addCell(brObj.getPrinter());
//
//        table_Data.addCell(getCellWithBorderNoBck("9", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Scanner");
//        table_Data.addCell(brObj.getScanner());
//
//        table_Data.addCell(getCellWithBorderNoBck("10", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Copier");
//        table_Data.addCell(brObj.getCopier());
//
//        table_Data.addCell(getCellWithBorderNoBck("11", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("No of Systems/ Lab");
//        table_Data.addCell(brObj.getNoOfSystems());
//
//        try {
//            subCatPart.add(table_Data);
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        // subCatPart.newPage();
//
//        try {
//
//            PdfPTable tableImg = new PdfPTable(2);
//            tableImg.setWidthPercentage(100);
//            PdfPCell c1 = new PdfPCell(new Phrase("PMKVY Poster Outside"));
//            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setBackgroundColor(new BaseColor(137, 138, 139));
//            tableImg.addCell(c1);
//
//            c1 = new PdfPCell(new Phrase("PMKVY Poster Inside"));
//            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setBackgroundColor(new BaseColor(137, 138, 139));
//            tableImg.addCell(c1);
//            tableImg.setHeaderRows(1);
//
//            tableImg.addCell(createImageCell(brObj.getPmkvyOutside()));
//            tableImg.addCell(createImageCell(brObj.getPmkvyInside()));
//
//            subCatPart.add(tableImg);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//
//        try {
//
//            PdfPTable tableImg = new PdfPTable(1);
//            tableImg.setWidthPercentage(100);
//            PdfPCell c1 = new PdfPCell(new Phrase("Electricity Bill"));
//            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setBackgroundColor(new BaseColor(137, 138, 139));
//            tableImg.addCell(c1);
//
//				 /* c1 = new PdfPCell(getCell("", Element.ALIGN_LEFT));
//				  c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//				  //c1.setBackgroundColor(new BaseColor(137, 138, 139));
//				  tableImg.addCell(c1);	*/
//            tableImg.setHeaderRows(1);
//
//            tableImg.addCell(createImageCell(brObj.getElectricityBill()));
//            //tableImg.addCell(getCell("", Element.ALIGN_LEFT));
//            subCatPart.add(tableImg);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//
//    }
//
//
//
//    private static void createTableCLI(Document document, ClassAndLabInfraBean adBean)
//            throws BadElementException {
//
//        float[] columnWidths = {.50f, 2.50f, 1.0f};
//        PdfPTable table_Data = new PdfPTable(columnWidths);
//        table_Data.setWidthPercentage(100);
//
//        table_Data.addCell(getCellWithBorderNoBck("1", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell(getCellWithBorderNormal("TSSC curriculum Alignment", PdfPCell.ALIGN_LEFT));
//        table_Data.addCell(getCellWithBorderNormal(adBean.getTsscCurriAlign(), PdfPCell.ALIGN_LEFT));
//
//        AdminInfraDetailsBean infraD = ApplicationUtil.getInstance().getAidBean();
//
//
//        table_Data.addCell(getCellWithBorderNoBck("2", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Projector");
//        table_Data.addCell(adBean.getProjectorInClass());
//
//        if(infraD!=null){
//
//            table_Data.addCell(getCellWithBorderNoBck("3", PdfPCell.ALIGN_CENTER));
//            table_Data.addCell("Total Area (In SqFt)");
//            table_Data.addCell(infraD.getTotalAreaOfCntr()+" SqFt.");
//
//		  /*table_Data.addCell(getCellWithBorderNoBck("7", PdfPCell.ALIGN_CENTER));
//		  table_Data.addCell("No of Lights");
//		  table_Data.addCell(infraD.get);
//
//		  table_Data.addCell(getCellWithBorderNoBck("8", PdfPCell.ALIGN_CENTER));
//		  table_Data.addCell("No of Fans");
//		  table_Data.addCell(adBean.getNoOfFansInClass());*/
//
//        }
//
//        table_Data.addCell(getCellWithBorderNoBck("4", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Classroom Area (In SqFt)");
//        table_Data.addCell(adBean.getTwoFourClassroomArea()+" SqFt.");
//
//        table_Data.addCell(getCellWithBorderNoBck("5", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("No of Classroom lights");
//        table_Data.addCell(adBean.getTwoFourNoOfClassroomLights());
//
//        table_Data.addCell(getCellWithBorderNoBck("6", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("No of Classroom fans");
//        table_Data.addCell(adBean.getTwoFourNoOfClassroomFans());
//
//        table_Data.addCell(getCellWithBorderNoBck("7", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Power Backups");
//        table_Data.addCell(adBean.getPowerBackUp());
//
//        table_Data.addCell(getCellWithBorderNoBck("8", PdfPCell.ALIGN_CENTER));
//        table_Data.addCell("Proper Ventilation");
//        table_Data.addCell(adBean.getProperVentilation());
//
//        try {
//            document.add(table_Data);
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//
//        try {
//
//            PdfPTable tableImg = new PdfPTable(2);
//            tableImg.setWidthPercentage(100);
//            PdfPCell c1 = new PdfPCell(new Phrase("External Centre TP"));
//            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setBackgroundColor(new BaseColor(137, 138, 139));
//            tableImg.addCell(c1);
//
//            c1 = new PdfPCell(new Phrase("Classroom Picture"));
//            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
//            c1.setBackgroundColor(new BaseColor(137, 138, 139));
//            tableImg.addCell(c1);
//            tableImg.setHeaderRows(1);
//
//            tableImg.addCell(createImageCell(adBean.getExternalTpArrangeImg()));
//            tableImg.addCell(createImageCell(adBean.getClassRoomImg()));
//
//            document.add(tableImg);
//
//        } catch (Exception e) {
//            // TODO: handle exception
//        }
//
//    }


    public String fileToBase64(String myPath) {
        String ba1 = null;

        System.out.println("..........myPath = "+myPath);
        try {

            FileInputStream mFileInputStream = new FileInputStream(myPath);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = mFileInputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byte[] ba = bos.toByteArray();

            ba1 = Base64.encodeToString(ba, Base64.DEFAULT);
        } catch (FileNotFoundException exception) {
            System.out.println("..........file not found");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ba1;
    }







    private class AsyncChangePass extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
            try {
                changePassBean = ApplicationUtil.getInstance().getWebservice().ChangePassword(changePassProxie, getActivity());
            }

            catch (ApplicationException e) {
                e.printStackTrace();
            }

            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd.setMessage("Loading..");
            pd.show();

            changePassProxie = new ChangePasswordProxie();
            changePassProxie.setStrEmpId(pref.getUserid());
            changePassProxie.setStrOldPass(inputOldPassword.getText().toString().trim());
            changePassProxie.setStrNewPass(inputNewPassword.getText().toString().trim());
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Make Progress Bar invisible
            //loginStatus = inputEmpid.getText().toString();

            pd.dismiss();
            //Error status is false

            if (changePassBean != null) {
                //Based on Boolean value returned from WebService
                   /* if (user.getEMPROLE().equals("admin")) {
                        //Navigate to Home Screen
                        mainIntent.putExtra("empType", "admin");
                        LoginScreen.this.startActivity(mainIntent);
                        LoginScreen.this.finish();
                    } else if (loginStatus.equals("employee")) {
                        //Navigate to Home Screen

                    } else {
                        //Set Error message
                        // Toast.makeText(LoginScreen.this,"Login Failed, try again",Toast.LENGTH_LONG).show();

                        mainIntent.putExtra("empType", "employee");
                        LoginScreen.this.startActivity(mainIntent);
                        LoginScreen.this.finish();
                    }*/

                //Error status is true
            }
            else{

                Toast.makeText(getActivity(),"Error occured in invoking webservice", Toast.LENGTH_LONG).show();
            }
            //Re-initialize Error Status to False
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }






}
