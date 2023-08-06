package com.pdftron.completereader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.multidex.BuildConfig;

import com.pdftron.common.PDFNetException;
import com.pdftron.demo.app.AdvancedReaderActivity;
import com.pdftron.demo.app.SimpleReaderActivity;
import com.pdftron.pdf.PDFNet;
import com.pdftron.pdf.config.PDFViewCtrlConfig;
import com.pdftron.pdf.config.ToolManagerBuilder;
import com.pdftron.pdf.config.ViewerConfig;
import com.pdftron.pdf.controls.DiffActivity;
import com.pdftron.pdf.controls.DocumentActivity;
import com.pdftron.pdf.controls.ThumbnailsViewFragment;
import com.pdftron.pdf.dialog.ViewModePickerDialogFragment;
import com.pdftron.pdf.utils.AppUtils;
import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
import com.pdftron.pdf.utils.Utils;

public class MainActivity extends AppCompatActivity {
String strbookname,strUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        strbookname = bundle.getString("bookname");
        strUrl = bundle.getString("bookpdf");
        openSimpleReaderActivity(true);


        try {
            PDFNet.initialize(this, R.raw.pdfnet, AppUtils.getLicenseKey(getApplicationContext()));
        } catch (PDFNetException e) {
            showLicenseRequestDialog();
        }

        PdfViewCtrlSettingsManager.setFollowSystemDarkMode(this, false);

        View newUiView = findViewById(R.id.simpleReaderLayoutNew);

        Button simpleReaderButtonNew = newUiView.findViewById(R.id.simpleReaderButton);
        simpleReaderButtonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSimpleReaderActivity(true);
            }
        });

        ImageView simpleReaderImageNew = newUiView.findViewById(R.id.simpleReaderImage);
        simpleReaderImageNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSimpleReaderActivity(true);
            }
        });

        Button completeReaderButton = findViewById(R.id.completeReaderButton);
        completeReaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompleteReaderActivity();
            }
        });

        ImageView completeReaderImage = findViewById(R.id.completeReaderImage);
        completeReaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCompleteReaderActivity();
            }
        });

        Button diffButton = findViewById(R.id.diffButton);
        diffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiffActivity();
            }
        });

        ImageView diffImage = findViewById(R.id.diffImage);
        diffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDiffActivity();
            }
        });
    }

    private void showLicenseRequestDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.missing_license_key)
                .setMessage(Html.fromHtml(getString(R.string.missing_license_key_msg)))
                .setCancelable(false)
                .create();
        dialog.show();
        ((TextView) dialog.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void openDiffActivity() {
        DiffActivity.open(this, R.raw.diff_doc_1, R.raw.diff_doc_2);
    }

    private void openCompleteReaderActivity() {
        PdfViewCtrlSettingsManager.setMultipleTabs(this, true);
        AdvancedReaderActivity.setDebug(BuildConfig.DEBUG);
        AdvancedReaderActivity.open(this);
    }

    private void openSimpleReaderActivity(boolean newUi) {
        PdfViewCtrlSettingsManager.setFollowSystemDarkMode(this, false); // for better dark mode experience in demo
        ToolManagerBuilder tmBuilder = ToolManagerBuilder.from()
                .setUseDigitalSignature(false)
                .setAutoResizeFreeText(false);
        int cutoutMode = 0;
        if (Utils.isPie()) {
            cutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        ViewerConfig.Builder builder = new ViewerConfig.Builder();
        builder = builder
                .fullscreenModeEnabled(true)
                .multiTabEnabled(true)
                .documentEditingEnabled(true)
                .longPressQuickMenuEnabled(false)
                .showPageNumberIndicator(true)
                .pageStackEnabled(true)
                .hideToolbars(new String[]{
                })
                .showBottomNavBar(true)
                .showTopToolbar(true)
                .showBottomToolbar(true)
                .permanentToolbars(false)
                .showThumbnailView(true)
                .showBookmarksView(true)
                .toolbarTitle("Pustak Market")
                .showSearchView(true)
                .showShareOption(false)
                .showDocumentSettingsOption(true)
                .showAnnotationToolbarOption(true)
                .showFormToolbarOption(true)
                .showFillAndSignToolbarOption(true)
                .showReflowOption(true)
                .showEditMenuOption(true)
                .showViewLayersToolbarOption(true)
                .showOpenFileOption(false)
                .showOpenUrlOption(false)
                .showEditPagesOption(true)
                .showPrintOption(false)
                .showSaveCopyOption(false)
                .hideThumbnailFilterModes(new ThumbnailsViewFragment.FilterModes[]{
                })
                .hideViewModeItems(new ViewModePickerDialogFragment.ViewModePickerItems[]{
                })
                .showCloseTabOption(true)
                .showAnnotationsList(true)
                .showOutlineList(true)
                .showUserBookmarksList(true)
                .navigationListAsSheetOnLargeDevice(true)
                .rightToLeftModeEnabled(false)

                .showRightToLeftOption(true)
                .openUrlCachePath(this.getCacheDir().getAbsolutePath())
                .saveCopyExportPath(this.getCacheDir().getAbsolutePath())
                .thumbnailViewEditingEnabled(true)
                .userBookmarksListEditingEnabled(true)
                .annotationsListEditingEnabled(true)
                .useStandardLibrary(false)
                .showToolbarSwitcher(true)
                .imageInReflowEnabled(true)
                .pdfViewCtrlConfig(PDFViewCtrlConfig.getDefaultConfig(this))
                .toolManagerBuilder(tmBuilder);
        if (Utils.isPie()) {
            builder = builder.layoutInDisplayCutoutMode(cutoutMode);
        }
        ViewerConfig config = builder.build();
        final Uri uri = Uri.parse(strUrl);
       // final Uri uri=  Uri.parse("https://pdftron.s3.amazonaws.com/downloads/pl/PDFTRON_mobile_about.pdf");

        Intent intent = DocumentActivity.IntentBuilder.fromActivityClass(this, SimpleReaderActivity.class)
                .usingConfig(config)
                .withUri(uri)

                .usingNewUi(newUi)
                .build();
        intent.putExtra("bookpdf",strUrl);
        startActivity(intent);
    }
}
